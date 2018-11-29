
window.addEventListener("load", ()=>{
    document.querySelector("#last").addEventListener('keypress', (e) => {
        keyPressed(e);
    });
});

function saveTransactions(userName){
    let form = document.querySelector("#Form");
    let allGroups = form.querySelectorAll(".transaction");
    if(checkIfNulls(form)) {
        createSnackBar("Nie wprowadzono żadnej wartości w polu");
        return;
    }
    console.log(allGroups);
    allGroups.forEach((element)=>{
        let arr = getAllValues(element);
        if(!arr)return false;
        let url = "http://localhost:8080/transactions/addTransaction?username="+userName+"&isApproved="+arr[0];
        fetch(url)
            .then(function(response) {
                return response.json();
            })
            .then(function(myJson) {
                createSnackBar(myJson.Message);
            });
    });

    removeChild(form);
    addFields();
}

function checkIfNulls(form){
    let allElements = form.querySelectorAll("input, select");
    for(let element of allElements){
        if(element.value === null || typeof element.value === "undefined" || element.value === ""){
            createSnackBar("Jedno z pól nie ma podanej wartości");
            return true;
        }
    }
    return false;
}

function getAllValues(element){
    let arr = [];
    let val = element.querySelectorAll("input, select");
    val.forEach((eachInput)=>{
        if(eachInput.value === null || typeof eachInput.value === "undefined" || eachInput.value === ""){
            createSnackBar("Jedno z pól nie ma podanej wartości");
            return false;
        }
        else arr.push(eachInput.value);
    });
    return arr;
}

function addManyFields(){
    let numberOfFields = document.querySelector("#numberOfFields").value;
    if(numberOfFields<=0){
        createSnackBar("Zmień liczbę pól");
        return;
    }
    for(let i = 0; i<numberOfFields; i++)addFields();

    if(numberOfFields>=5&&numberOfFields!==1)createSnackBar("Utworzono "+numberOfFields+" pól");
    else if (numberOfFields===1)createSnackBar("Utworzono "+numberOfFields+" pole");
    else createSnackBar("Utworzono "+numberOfFields+" pola");
}

function keyPressed(e){
    if(e.keyCode===13||e.keyCode===9) {
        addFields();
    }
}

function addFields(){
    let container = document.getElementById("Form");
    let div = createDiv();
    createInputs(4, div, container);
    //button
    div.appendChild(createButton(div));
    div.appendChild(document.createElement("br"));
    container.appendChild(div);
}

function createDiv(){
    return addClassesToElement(document.createElement("div"), "col-auto d-block transaction");
}

function createInputs(numberOfRows, div, container){
    let selectBox = document.createElement("select");
    selectBox.innerHTML = "<option value='true'>Tak</option><option value='false'>Nie</option>";
    addClassesToElement(selectBox, "form-control mb-2 mr-2");
    div.appendChild(selectBox);
    let input;
    for (let i=0;i<numberOfRows;i++){
        input = document.createElement("input");
        input.type = "text";
        input.name = "member" + i;
        addClassesToElement(input, "form-control mb-2 mr-2");
        div.appendChild(input);
        container.appendChild(div);
    }
    input.addEventListener('keypress', (e) => {
        keyPressed(e);
    });
}

function createButton(obj){
    let button = document.createElement("button");
    button.classList.add("btn");
    button.classList.add("btn-danger");
    button.innerHTML = "X";
    button.onclick = ()=>{
        removeFields(obj);
    };
    return button;
}

function removeFields(obj){
    let container = document.getElementById("Form");
    container.removeChild(obj);
}