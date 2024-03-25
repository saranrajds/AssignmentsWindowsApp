
package com.saran.interivewdb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.saran.enums.ErrorCode;
import com.saran.enums.InterviewProgressStatus;
import com.saran.enums.ModuleType;
import com.saran.enums.UserType;
import com.saran.enums.UserValidation;
import com.saran.model.Candidate;
import com.saran.model.Credentials;
import com.saran.model.HR;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InterviewDB {

	private static InterviewDB interivewDB;
	private ArrayList<Candidate> candidates = new ArrayList<>();
	private ArrayList<Credentials> credentials = new ArrayList<Credentials>();
	private int candidatePrimaryID = 1;
	private ArrayList<HR> hrs = new ArrayList<HR>();
	private int hrPrimaryID = 1;
	private final String FILE_PATH = "src/com/saran/interivewdb/dbFiles/";
	private boolean isCandidaFileRetrived = false, isHRFileRetrived = false;
	private int currentUserId = UserType.INVALID.getUserType();
	
	public boolean isCandidaFileRetrived() {
		return isCandidaFileRetrived;
	}

	public void setCandidaFileRetrived(boolean isCandidaFileRetrived) {
		this.isCandidaFileRetrived = isCandidaFileRetrived;
	}

	public boolean isHRFileRetrived() {
		return isHRFileRetrived;
	}

	public void setHRFileRetrived(boolean isHRFileRetrived) {
		this.isHRFileRetrived = isHRFileRetrived;
	}

	public static InterviewDB getInstance() {

		if (interivewDB == null)
			interivewDB = new InterviewDB();
		return interivewDB;
	}
	
	public int isValidCredential(Credentials credential) {

		boolean hasUser = credentials.stream().anyMatch(user -> user.getUserName().equals(credential.getUserName())
				&& user.getPassword().equals(credential.getPassword()));

		if (hasUser) {

			currentUserId = UserType.USER.getUserType();
			return UserType.USER.getUserType();
		} else if (credential.getUserName().equals("123") && credential.getPassword().equals("123")) {

			currentUserId = UserType.ADMIN.getUserType();
			return UserType.ADMIN.getUserType();
		}
		currentUserId = UserType.INVALID.getUserType();
		return UserType.INVALID.getUserType();
	}
	public boolean addNewCandidate(Candidate candidate) {

		if (candidate.getCandidateId() == -1) {
			candidate.setCandidateId(candidatePrimaryID++);
			candidate.setInterviewStatus(InterviewProgressStatus.NOTSTART.getInterviewProgressStatus());
			candidates.add(candidate);
			System.out.println(candidates.size());
			uploadData("candidate", candidates);
			return true;
		} else {
			return true;
		}
	}

	public boolean updateNewCandidate(Candidate pCandidate) {

		boolean isUpdated = false;

		for (Candidate candidate : candidates) {

			if (candidate.getEmailId().equals(pCandidate.getEmailId())) {
				candidate.setName(pCandidate.getName());
				candidate.setMobileNumber(pCandidate.getMobileNumber());
//				candidate.setEmailId(pCandidate.getEmailId());
				isUpdated = true;
			}
		}
		uploadData("candidate", candidates);

		return isUpdated;
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public boolean checkCandidateExits(String candidateEmail) {
		return candidates.stream().anyMatch(candidate -> candidate.getEmailId().equals(candidateEmail));
	}

	public boolean removeCandidate(String candidateEmail) {

		ArrayList<Candidate> tempCandidate = new ArrayList<Candidate>();

		for (Candidate candidate : candidates) {

			if (!candidate.getEmailId().equals(candidateEmail)) {
				tempCandidate.add(candidate);
			}
		}

		candidates = tempCandidate;
		uploadData("candidate", candidates);
		return candidates.stream().anyMatch(cnd -> cnd.getEmailId().equals(candidateEmail));
	}

	public boolean checkHRExits(String hrEmailId) {
		return hrs.stream().anyMatch(hr -> hr.getEmail().equals(hrEmailId));
	}

	public boolean updateNewHR(HR pHR) {

		boolean isUpdated = false;

		for (HR hr : hrs) {

			if (hr.getEmail().equals(pHR.getEmail())) {
				hr.setName(pHR.getName());
				isUpdated = true;
				break;
			}
		}

		uploadData("hr", hrs);
		return isUpdated;
	}

	public boolean addNewHR(HR pHR) {

		System.out.println("hrs -> " + hrs);
		if (hrs.stream().anyMatch(hr -> hr.getName().equals(pHR.getName())))
			return false;

		if (pHR.getId() == -1) {
			pHR.setId(hrPrimaryID++);
			hrs.add(pHR);
		}
		uploadData("hr", hrs);
		return true;
	}

	public boolean removeHR(String hrEmail) {

		ArrayList<HR> temHr = new ArrayList<HR>();
		for (HR hr : hrs) {

			if (hr.getEmail().equals(temHr))
				temHr.add(hr);
		}

		hrs = temHr;
		uploadData("hr", hrs);
		return hrs.stream().anyMatch(hr -> hr.getEmail().equals(temHr));
	}

	public List<HR> getHRList() {
		return hrs;
	}

	public List<HR> getHRByName(String hrName) {
		return hrs.stream().filter(hr -> hr.getName().equals(hrName)).toList();
	}

	public List<Candidate> getCandidatesByName(String candidateName) {
		return candidates.stream().filter(candidate -> candidate.getName().equals(candidateName)).toList();
	}

	public int checkHRNameIsValid(String hrName) {

		if (!hrs.stream().anyMatch(hr -> hr.getName().equals(hrName))) {
			return ErrorCode.NOTMATCHING.getErrorCode();
		}

		int hrId = hrs.stream().filter(hr -> hr.getName().equals(hrName)).toList().get(0).getId();

		if (candidates.stream().anyMatch(hr -> hr.getInterviewProgressStatus() == InterviewProgressStatus.INPROGRESS
				.getInterviewProgressStatus())) {
			return ErrorCode.INTERVIEWGOINGON.getErrorCode();
		}

		return ErrorCode.SUCCESS.getErrorCode();
	}

	public int checkCandidateIsValid(String candidateName) {

		if (candidates.stream().anyMatch(hr -> hr.getName().equals(candidateName))) {

			int status = InterviewProgressStatus.NOTSTART.getInterviewProgressStatus();

			if (candidates.stream().anyMatch(
					cnd -> cnd.getName().equals(candidateName) && cnd.getInterviewProgressStatus() == status)) {
				return UserValidation.VALID.getUserValidation();
			}
			return InterviewProgressStatus.ALREADYATTEND.getInterviewProgressStatus();
		}

		return UserValidation.NOVALID.getUserValidation();
	}

	public void updateInterview(String hrName, String candidateName) {

		int hrId = hrs.stream().filter(hr -> hr.getName().equals(hrName)).toList().get(0).getId();

		ArrayList<Candidate> updatedCandidate = (ArrayList<Candidate>) candidates.stream().map(candidate -> {
			if (candidate.getName().equals(candidateName)) {
				candidate.setInterviewProgressStatus(InterviewProgressStatus.INPROGRESS.getInterviewProgressStatus());
				candidate.setHrId(hrId);
			}
			return candidate;
		}).collect(Collectors.toList());
		candidates = updatedCandidate;
		uploadData("candidate", candidates);
	}

	public Candidate onGetCurrentInterviewStatus() {

		int statusId = InterviewProgressStatus.INPROGRESS.getInterviewProgressStatus();
		Candidate candidate = null;
		if (candidates.stream().anyMatch(cnd -> cnd.getInterviewProgressStatus() == statusId))
			candidate = candidates.stream().filter(cnd -> cnd.getInterviewProgressStatus() == statusId).findFirst()
					.get();

		return candidate;
	}

	public String getHrNameById(int hrId) {
		return hrs.stream().filter(hr -> hr.getId() == hrId).toList().get(0).getName();
	}

	public boolean updateInterviewStatus(int currentCandidateId) {

		ArrayList<Candidate> updatedCandidate = (ArrayList<Candidate>) candidates.stream().map(candidate -> {
			if (candidate.getCandidateId() == currentCandidateId) {
				candidate.setInterviewProgressStatus(InterviewProgressStatus.COMPLETED.getInterviewProgressStatus());
			}
			return candidate;
		}).collect(Collectors.toList());
		candidates = updatedCandidate;
		uploadData("candidate", candidates);
		return true;
	}

	public <T> void uploadData(String pFilename, ArrayList<T> arrayList) {

		String filename = FILE_PATH + pFilename + ".json";
		ObjectMapper objectMapper = new ObjectMapper();
		boolean fileValid = createFile(pFilename);

		if (fileValid) {
			System.out.println(arrayList.size());
			try {
				String jsonData = objectMapper.writeValueAsString(arrayList);
			} 
			catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				objectMapper.writeValue(new File(filename), arrayList);
			} catch (StreamWriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabindException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean createFile(String filename) {

		File file = new File(filename);

		try {
			if (file.exists()) {
				if (file.delete()) {
//					System.out.println("Existing file deleted.");
				} else {
//					System.out.println("Failed to delete the existing file.");
					return false;
				}
			}

			if (file.createNewFile()) {
//				System.out.println("File created: " + file.getName());
				return true;
			} else {
//				System.out.println("Failed to create the file.");
			}
			return false;
		} catch (IOException e) {
//			System.out.println("An error occurred.");
			e.printStackTrace();
			return false;
		}
	}

	public void retriveDataFromFile(int moduleType) {

		String filename = FILE_PATH;

		if (moduleType == ModuleType.CANDIDATE.getModuleType()) {
			filename += "candidate.json";
		} else {
			filename += "hr.json";
		}

		ObjectMapper objectMapper = new ObjectMapper();

		try {

			File file = new File(filename);
			if (file.exists()) {

				if (moduleType == ModuleType.CANDIDATE.getModuleType()) {
					ArrayList<Candidate> candidatesTemp = objectMapper.readValue(new File(filename),
							new TypeReference<ArrayList<Candidate>>() {
							});
					candidates = candidatesTemp;
				} else {
					ArrayList<HR> hrsTemp = objectMapper.readValue(new File(filename), new TypeReference<ArrayList<HR>>() {
					});
					hrs = hrsTemp;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
