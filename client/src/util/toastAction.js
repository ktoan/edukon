import { toast } from "react-toastify";

export function showToastSuccess(message) {
  toast.success(message);
}

export function showToastError(message) {
  toast.error(message);
}

export function showToastWarning(message) {
  toast.warning(message);
}

export function showToastInfo(message) {
  toast.info(message);
}
