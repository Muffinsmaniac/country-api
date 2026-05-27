
document.addEventListener("DOMContentLoaded", function(){

    renderCountries("http://localhost:8080/api/countries")


    document.getElementById('nameButton').addEventListener("click", function(){
        document.getElementById("mainnavlist").innerHTML = ""; //Clear list
        let region = document.getElementById("region").value;

        if(region==="all"){
            renderCountries("http://localhost:8080/api/countries/name");
        }
        else{
            renderCountries("http://localhost:8080/api/countries/name/"+region);
        }

    })

    document.getElementById('popButton').addEventListener('click', function(){
        document.getElementById("mainnavlist").innerHTML = ""; //Clear list
        let region = document.getElementById("region").value;

        if(region==="all"){
            renderCountries("http://localhost:8080/api/countries/population");
        }
        else{
            renderCountries("http://localhost:8080/api/countries/population/"+region);
        }
    })

    function renderCountries(url){
        fetch(url)
            .then(function(response){
                return response.json();
            })
            .then(function(object) {
                let list = document.getElementById("mainnavlist");

                for(let country of object.content){
                    let li = document.createElement("li");
                    li.textContent = country.name + " " + country.population;
                    list.appendChild(li);
                }
            })
    }
})