package com.transferwise.banks.demo.credentials.twclient;

class TWUserResponse {

    private Long id;
    private String email;
    private Boolean active;

    public TWUserResponse() {
    }

    public TWUserResponse(Long id, String email, Boolean active) {
        this.id = id;
        this.email = email;
        this.active = active;
    }

    public Long getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    public Boolean getActive() {
        return active;
    }

}
