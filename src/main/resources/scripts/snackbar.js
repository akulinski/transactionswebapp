function createSnackBar(str){
    let snackbar = document.getElementById("snackbar");
    snackbar.className = "show";
    snackbar.innerHTML=str;
    setTimeout(function(){ snackbar.className = snackbar.className.replace("show", ""); }, 3000);
}