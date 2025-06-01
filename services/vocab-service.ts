// src/services/vocabService.ts
import apiClient from "./apiClient"; // Import the centralized apiClient
import { AddVocabRequest, AddVocabResponse, VocabularyItem } from "./apiTypes";

/**
 * Service for interacting with the Vocabulary API endpoints.
 * Provides methods for managing vocabulary items.
 */
export const vocabService = {
	/**
	 * Adds a new vocabulary item for a given user.
	 * @param request The AddVocabRequest object containing userID, knownWord, and targetWord.
	 * @returns A promise that resolves with the AddVocabResponse on success.
	 */
	async addVocabulary(request: AddVocabRequest): Promise<AddVocabResponse> {
		const response = await apiClient.post<AddVocabResponse>("/Vocab", request);
		return response.data;
	},

	/**
	 * Retrieves all vocabulary items for a specific user.
	 * @param userId The ID of the user.
	 * @returns A promise that resolves with an array of VocabularyItem objects.
	 */
	async getAllVocabulary(userId: number): Promise<VocabularyItem[]> {
		const response = await apiClient.get<VocabularyItem[]>(`/Vocab/all/${userId}`);
		return response.data;
	},

	/**
	 * Retrieves a single vocabulary item by its ID for a specific user.
	 * @param userId The ID of the user.
	 * @param vocabId The ID of the vocabulary item.
	 * @returns A promise that resolves with a VocabularyItem object.
	 */
	async getVocabularyByID(userId: number, vocabId: number): Promise<VocabularyItem> {
		const response = await apiClient.get<VocabularyItem>(`/Vocab/${userId}/${vocabId}`);
		return response.data;
	},

	/**
	 * Retrieves all "unknown" (not yet learnt) vocabulary items for a specific user.
	 * @param userId The ID of the user.
	 * @returns A promise that resolves with an array of VocabularyItem objects.
	 */
	async getUnknownVocabulary(userId: number): Promise<VocabularyItem[]> {
		const response = await apiClient.get<VocabularyItem[]>(`/Vocab/unknown/${userId}`);
		return response.data;
	},

	/**
	 * Retrieves all "known" (learnt) vocabulary items for a specific user.
	 * @param userId The ID of the user.
	 * @returns A promise that resolves with an array of VocabularyItem objects.
	 */
	async getKnownVocabulary(userId: number): Promise<VocabularyItem[]> {
		const response = await apiClient.get<VocabularyItem[]>(`/Vocab/known/${userId}`);
		return response.data;
	},

	/**
	 * Deletes a specific vocabulary item for a user.
	 * @param userId The ID of the user.
	 * @param vocabId The ID of the vocabulary item to delete.
	 * @returns A promise that resolves with a message indicating success or failure.
	 */
	async deleteVocabulary(userId: number, vocabId: number): Promise<{ message: string }> {
		const response = await apiClient.delete<{ message: string }>(`/Vocab/${userId}/${vocabId}`);
		return response.data;
	},
};
