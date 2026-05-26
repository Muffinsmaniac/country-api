
var baseUrl = "http://localhost:8080/"
document.addEventListener("DOMContentLoaded", function(){
    fetch(baseUrl+"api/countries")
        .then(function(response){
            return response.json();
        })
        .then(function(object) {
            let list = document.getElementById("mainnavlist");
            for(let country of object){
               let li = document.createElement("li");
               li.textContent = country.name;
               list.appendChild(li);
            }
        })
})