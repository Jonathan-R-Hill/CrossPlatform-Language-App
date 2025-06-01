import axios from "axios";
import { API_URL } from "../config.json";
import { ApiError } from "./apiTypes";

/**
 * Global Axios instance configured with base URL and common headers.
 * This instance is used for all API calls throughout the application.
 */
const apiClient = axios.create({
	baseURL: API_URL,
	headers: {
		"Content-Type": "application/json",
		// You can add other common headers here, e.g., 'Accept': 'application/json'
	},
});

/**
 * Configures an Axios response interceptor for centralized error handling.
 * This intercepts all responses, checks for errors, logs them, and
 * throws a new Error with a more friendly message.
 */
apiClient.interceptors.response.use(
	(response) => response,
	(error) => {
		if (error.response) {
			// The request was made and the server responded with a status code
			// that falls out of the range of 2xx. The error.response.data
			const apiError: ApiError = error.response.data;
			console.error(
				"API Error Response:",
				error.response.status,
				apiError.message || error.message,
				error.response.data,
			);
			throw new Error(apiError.message || `API error! Status: ${error.response.status}`);
		} else if (error.request) {
			// The request was made but no response was received
			console.error("Network Error: No response received from server.", error.message);
			throw new Error("No response from server. Please check your network connection.");
		} else {
			// Something happened in setting up the request
			console.error("Request Setup Error:", error.message);
			throw new Error(`An unexpected error occurred: ${error.message}`);
		}
	},
);

export default apiClient;
