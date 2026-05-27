
document.addEventListener("DOMContentLoaded", function(){
    let currentPage= 0; //Keeping track on what page we are using.
    let currentSorting = "name";
    let currentRegion ="all";
    let descending = false;

    renderCountries()


    document.getElementById('nameButton').addEventListener("click", function(){
        currentRegion = document.getElementById("region").value;
        currentSorting = "name";
        currentPage= 0;
        renderCountries();
    })

    document.getElementById('popButton').addEventListener('click', function(){
        currentRegion = document.getElementById("region").value;
        currentSorting = "population";
        currentPage= 0;
        renderCountries();
    })

    document.getElementById('prevButton').addEventListener('click',function(){
        currentPage--;
        renderCountries();
    })

    document.getElementById('nextButton').addEventListener('click',function(){
        currentPage++;
        renderCountries();
    })

    document.getElementById('directionButton').addEventListener('click',function(){
        currentPage= 0;
        if(descending){
            descending = false;
            this.textContent = "Ascending"
        }
        else{
            descending = true;
            this.textContent = "Descending"
        }
        renderCountries();
    })



    function renderCountries(){
        //We first build the URL we need.
        let url = "http://localhost:8080/api/countries/"+ currentSorting;
        if(currentRegion !== "all"){
            url += "/" + currentRegion;
        }
        url+= "?page="+currentPage + "&descending="+descending;

        fetch(url)
            .then(function(response){
                return response.json();
            })
            .then(function(object) {
                let list = document.getElementById("mainnavlist");
                list.innerHTML = ""; //Clear list!

                for(let country of object.content){
                    let li = document.createElement("li");
                    li.textContent = country.name + " " + country.population;
                    list.appendChild(li);
                }
                updatePageButtons(object);
            })
    }

    function updatePageButtons(object){
        document.getElementById('prevButton').disabled = object.first;
        document.getElementById('nextButton').disabled = object.last;
    }

})