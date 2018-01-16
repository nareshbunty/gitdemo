package com.app.dto;

public class TblCampusTo {
	 private Integer id;
	    private String campusCode;
	    private String campusName;
	    
		public TblCampusTo() {
			super();
		}
		
		public TblCampusTo(Integer id) {
			super();
			this.id = id;
		}

		public TblCampusTo(Integer id, String campusCode, String campusName) {
			super();
			this.id = id;
			this.campusCode = campusCode;
			this.campusName = campusName;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getCampusCode() {
			return campusCode;
		}
		public void setCampusCode(String campusCode) {
			this.campusCode = campusCode;
		}
		public String getCampusName() {
			return campusName;
		}
		public void setCampusName(String campusName) {
			this.campusName = campusName;
		}

		@Override
		public String toString() {
			return "TblCampusTo [id=" + id + ", campusCode=" + campusCode + ", campusName=" + campusName + "]";
		}
	    
		
}
