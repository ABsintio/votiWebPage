function visible() {
    var passwd = document.getElementById("psinput");
    if (passwd.type === "password"){
        passwd.type = "text";
    } else {
        passwd.type = "password";
    }
}

function visible2() {
    var confirmation = document.getElementById("psinputconf");
    if (confirmation.type === "password"){
        confirmation.type = "text";
    } else {
        confirmation.type = "password";
    }
}

function confirm(){
    var confirmation = document.getElementById("psinputconf");
    var passwd = document.getElementById("psinput");

    if (passwd.value != "") {
        if (confirmation.value == passwd.value) {
            confirmation.style.borderColor = "green";
            confirmation.setCustomValidity("");
        } else {
            confirmation.style.borderColor = "red";
            confirmation.setCustomValidity("Le due password devono coincidere");
        }
    } else {
        confirmation.style.borderColor = "red";
        passwd.style.borderColor = "red";
    }
}

function passwdord() {
    var passwd = document.getElementById("psinput");

    if (passwd.value.length > 0) {
        passwd.style.borderColor = "green";
    } else {
        passwd.style.borderColor = "red";
    }
}

function checkMatricola(){
    var matr = document.getElementById("matricola");
    var numbers = /^[0-9]{7}$/;

    if (matr.value.match(numbers)){
        matr.setCustomValidity('');
    } else {
        matr.setCustomValidity('La matricola non contiene lettere e deve essere di 7 caratteri');
    }
}