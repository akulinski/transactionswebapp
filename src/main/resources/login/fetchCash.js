
document.onload =  function() {
    document.querySelector("#store-selector").onchange = onStoreSelect();
};


function onStoreSelect(){

    let selectShop = document.getElementById("store-selector");
    let selectCash = document.getElementById("cash-selector");
    selectCash.innerHTML=""; //Erasing all inside of select

    fetch('http://localhost:8080/getAllCashes?shopId='+selectShop.value)
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            myJson.forEach((element)=>{
                console.log(element.cashNumber);
                let option = document.createElement("option");
                option.text = element.cashNumber;
                option.value = element.cashNumber;
                selectCash.add(option);
            });
        });
}