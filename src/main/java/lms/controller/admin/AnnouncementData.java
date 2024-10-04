package lms.controller.admin;

public class AnnouncementData {
        private String aTitle;
        private String aDescription;

        public AnnouncementData(String aTitle, String aDescription) {
            this.aTitle = aTitle;
            this.aDescription = aDescription;
        }

        public String getaTitle() {
            return aTitle;
        }

        public String getaDescription() {
            return aDescription;
        }
}
