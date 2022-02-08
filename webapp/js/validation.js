function validation() {
    const pass = document.getElementById("pass");
    if (!pass.checkValidity()) {
        document.getElementById("pass-mes").innerHTML = "Пароль должен содержать как минимум одну букву в верхнем регистре" +
            " и минимум две цифры<br>"
        event.preventDefault();
    }
    const rPass = document.getElementById("r_pass");
    if(rPass.value!==pass.value){
        document.getElementById("not-match").innerHTML = "Пароли не совпадают<br>"
        event.preventDefault();
    }
    const tel = document.getElementById("tel");
    if(!tel.checkValidity()){
        document.getElementById("tel-mes").innerHTML = "Номер телефона должен начинаться со знака +," +
            "далее должны следовать минимум 6 цифр<br>"
        event.preventDefault();
    }
}
