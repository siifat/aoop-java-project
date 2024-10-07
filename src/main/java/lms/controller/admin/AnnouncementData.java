package lms.controller.admin;

public class AnnouncementData {
        private String aNo;
        private String aDate;
        private String aTitle;
        private String aDescription;

        public AnnouncementData(String aNo, String aDate, String aTitle, String aDescription) {
            this.aNo = aNo;
            this.aDate = aDate;
            this.aTitle = aTitle;
            this.aDescription = aDescription;
        }

        public String getANo() {
            return aNo;
        }

        public String getADate() {
            return aDate;
        }

        public String getATitle() {
            return aTitle;
        }

        public String getADescription() {
            return aDescription;
        }
}
