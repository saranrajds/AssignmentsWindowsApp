package com.saran.reportmanagement;

import java.util.List;

import com.saran.interivewdb.InterviewDB;
import com.saran.model.Candidate;
import com.saran.model.HR;

public class ReportModel {

	private ReportView reportView;
	
	public ReportModel(ReportView reportView) {
		this.reportView = reportView;
	}

	public List<Candidate> getCandidates() {
		
		List<Candidate> candidates = InterviewDB.getInstance().getCandidates();
		reportView.showCandidateResult(candidates);
		return candidates;
	}
	
	public void getCandidatesByName(String candidateName) {
		
		List<Candidate> candidates = InterviewDB.getInstance().getCandidatesByName(candidateName);
		reportView.showCandidateResult(candidates);
	}

	public List<HR> getHR() {
		List<HR> hrs = InterviewDB.getInstance().getHRList();
		reportView.showHRResult(hrs);
		return hrs;
	}
	
	public void getHRByName(String hrName) {
		List<HR> hrs = InterviewDB.getInstance().getHRByName(hrName);
		reportView.showHRResult(hrs);
	}

}
