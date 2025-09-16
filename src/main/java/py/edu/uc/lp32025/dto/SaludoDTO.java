package py.edu.uc.lp32025.dto;

    public class SaludoDTO extends BaseDTO {
        private String saludo;

        public SaludoDTO() {}

        public SaludoDTO(int status, String errorCode, String userMessage, String saludo) {
            super(status, errorCode, userMessage);
            this.saludo = saludo;
        }
        public SaludoDTO(String saludo) {
            super(0, "", ""); // status=0, sin errorCode, sin userMessage
            this.saludo = saludo;
        }

        public String getSaludo() {
            return saludo;
        }

        public void setSaludo(String saludo) {
            this.saludo = saludo;
        }
    }