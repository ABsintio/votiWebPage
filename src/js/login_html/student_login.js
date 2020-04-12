function visible() {
    var inputPass = document.getElementById("input");
    if (inputPass.type === "password") {
        inputPass.type = "text";
    } else {
        inputPass.type = "password";
    }
}