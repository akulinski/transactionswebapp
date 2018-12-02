const endPoint = "/manageothers/getusers";
document.onload =  function() {
    document.querySelector("#store-selector").onchange = onStoreSelect();
};

function onUserSelect(){
    let selectUser = document.getElementById("user-selector");
    fetch('http://localhost:8080/manageothers/getinfo?userName='+selectUser.value)
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            putValues(myJson);
        });
}

function putValues(element){
    let currStore = document.querySelector("#store-selector");

    let inputId = document.querySelector("#ID");
    let inputUsername = document.querySelector("#username");
    let inputEmail = document.querySelector("#email");
    let inputPassword = document.querySelector("#password");
    let inputStore = document.querySelector("#store-changer");

    inputUsername.value = element.login;
    inputEmail.value = element.email;
    inputPassword.value = "";
    inputId.value = element.id;
    inputStore.value = currStore.options[currStore.selectedIndex].value;
}

function onStoreSelect(){
    let selectShop = document.getElementById("store-selector");
    let selectUser = document.getElementById("user-selector");
    selectUser.innerHTML=""; //Erasing all inside of select
    fetch('http://localhost:8080/users/getUsers?shopId='+selectShop.value)
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            myJson.forEach((element)=>{
                let option = document.createElement("option");
                option.text = element.login;
                option.value = element.login;
                selectUser.add(option);
            });
            if(myJson!=null)onUserSelect();
        });
}

function saveChanges(){
    let obj = {};
    obj['ID'] = document.querySelector("#ID").value;
    obj['Username'] = document.querySelector("#username").value;
    obj['password'] = document.querySelector("#password").value;
    obj['passwordRepeat'] = document.querySelector("#passwordRepeat").value;
    obj['email'] = document.querySelector("#email").value;
    obj['store'] = document.querySelector("#store-changer").value;
    obj['privilege'] = document.querySelector("#privilege").value;
    let url = "http://localhost:8080/manageothers/updateuser"+serialize(obj);
    console.log(url);
    fetch(url)
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            console.log(myJson);
            createSnackBar(myJson.Message);
        });
}

function addNewUser(){
    let obj = {};
    obj['Username'] = document.querySelector("#newUsername").value;
    obj['password'] = document.querySelector("#newPassword").value;
    obj['passwordRepeat'] = document.querySelector("#newPasswordRepeat").value;
    obj['email'] = document.querySelector("#newEmail").value;
    obj['store'] = document.querySelector("#newStore-changer").value;
    obj['privilege'] = document.querySelector("#newPrivilege").value;
    let url = "http://localhost:8080/manageothers/adduser"+serialize(obj);
    console.log(url);
    fetch(url)
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            console.log(myJson);
            createSnackBar(myJson.Message);
        });
}

