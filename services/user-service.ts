// src/services/userService.ts
import apiClient from "./apiClient"; // Import the centralized apiClient
import { AddUserRequest, AddUserResponse, GetUserIdResponse, DeleteUserResponse } from "./apiTypes";

/**
 * Service for interacting with the User API endpoints.
 * Provides methods for adding, retrieving, and deleting user data.
 */
export const userService = {
	/**
	 * Sends a POST request to add a new user to the system.
	 * @param {AddUserRequest} request - The user data to be added, including firstName, lastName, and userName.
	 * @returns {Promise<AddUserResponse>} A promise that resolves with the response containing a message and the new user's ID.
	 * @throws {Error} If the API call fails or returns an error.
	 */
	async addUser(request: AddUserRequest): Promise<AddUserResponse> {
		const response = await apiClient.post<AddUserResponse>("/User", request);
		return response.data;
	},

	/**
	 * Sends a GET request to retrieve a user's ID by their username.
	 * @param {string} userName - The username of the user to retrieve the ID for.
	 * @returns {Promise<GetUserIdResponse>} A promise that resolves with the response containing the user's ID.
	 * @throws {Error} If the API call fails, the user is not found, or an error occurs.
	 */
	async getUserIdByUserName(userName: string): Promise<GetUserIdResponse> {
		const response = await apiClient.get<GetUserIdResponse>(`/User/${userName}`);
		return response.data;
	},

	/**
	 * Sends a DELETE request to remove a user from the system by their user ID.
	 * @param {number} userId - The ID of the user to be deleted.
	 * @returns {Promise<DeleteUserResponse>} A promise that resolves with a message indicating the success or failure of the deletion.
	 * @throws {Error} If the API call fails, the user is not found, or an error occurs.
	 */
	async deleteUser(userId: number): Promise<DeleteUserResponse> {
		const response = await apiClient.delete<DeleteUserResponse>(`/User/${userId}`);
		return response.data;
	},
};
