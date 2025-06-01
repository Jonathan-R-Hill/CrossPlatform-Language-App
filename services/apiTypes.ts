//#region User Service Types
export interface AddUserRequest {
	firstName: string;
	lastName: string;
	userName: string;
}

export interface AddUserResponse {
	userId: number;
}

export interface GetUserIdResponse {
	userId: number;
}

export interface DeleteUserResponse {
	message: string;
}
//#endregion

// ------------------------------------------------------- //

//#region Vocabulary Service Types
export interface AddVocabRequest {
	userID: number;
	knownWord: string;
	targetWord: string;
}

export interface AddVocabResponse {
	message: string;
	vocabId: number;
}

export interface VocabularyItem {
	ID: number;
	UserID: number;
	"KnownLanguage-Word": string;
	"TargetLanguage-Word": string;
	CreatedDate: string;
	Learnt: number;
	LastPracticed: string;
	NextPractice: string;
}

//#endregion

export interface ApiError {
	message: string;
}
