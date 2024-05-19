from flask import Flask, request, jsonify
from werkzeug.utils import secure_filename
from flask_cors import CORS, cross_origin
import os
from transformers import PegasusForConditionalGeneration, PegasusTokenizer, pipeline
import docx

app = Flask(__name__)
# Set a directory for saving uploaded files
UPLOAD_FOLDER = 'uploads'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
os.makedirs(UPLOAD_FOLDER, exist_ok=True)

# Initialize the Pegasus model and tokenizer
model_name = "google/pegasus-xsum"
tokenizer = PegasusTokenizer.from_pretrained(model_name)
model = PegasusForConditionalGeneration.from_pretrained(model_name)

def read_text_from_file(file_path):
    if file_path.endswith('.txt'):
        with open(file_path, 'r', encoding='utf-8') as file:
            return file.read()
    elif file_path.endswith('.docx'):
        doc = docx.Document(file_path)
        full_text = []
        for para in doc.paragraphs:
            full_text.append(para.text)
        return '\n'.join(full_text)
    else:
        raise ValueError("Unsupported file type. Please use a .txt or .docx file")

def chunk_text(text, chunk_size):
    words = text.split()
    chunks = [" ".join(words[i:i + chunk_size]) for i in range(0, len(words), chunk_size)]
    return chunks

@app.route('/summarize', methods=['POST'])
@cross_origin(origins='http://localhost:3000')


def summarize_text():
    if 'file' not in request.files:
        return jsonify({"error": "No file part"}), 400

    file = request.files['file']

    if file.filename == '':
        return jsonify({"error": "No selected file"}), 400

    if file:
        filename = secure_filename(file.filename)
        file_path = os.path.join(app.config['UPLOAD_FOLDER'], filename)
        file.save(file_path)

        try:
            text = read_text_from_file(file_path)
            chunks = chunk_text(text, 100)
            summaries = []
            for chunk in chunks:
                tokens = tokenizer(chunk, truncation=True, max_length=512, return_tensors="pt")
                target_length = int(0.3 * tokens.input_ids.size(1))
                summary_ids = model.generate(tokens["input_ids"], min_length=target_length, max_length=target_length + 10)
                summary = tokenizer.decode(summary_ids[0], skip_special_tokens=True)
                summaries.append(summary)
            final_summary = " ".join(summaries)
        finally:
            os.remove(file_path)

        return jsonify({"summary": final_summary})

if __name__ == '__main__':
    app.run(debug=True, port=5000)
