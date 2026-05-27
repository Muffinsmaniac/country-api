
document.addEventListener("DOMContentLoaded", function(){
    let currentPage= 0; //Keeping track on what page we are using.
    let currentSorting = "name";
    let currentRegion ="all";
    let descending = false;
    let visitedCountries = new Set(); //Since mocked user, the user has no visited countries when the app start.

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
        let url = urlBuilder();

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

                    //Adding a checkbox to handle if the country is visited or not.
                    let checkbox = document.createElement("input");
                    checkbox.type = "checkbox";
                    checkbox.addEventListener("change", function(){
                        if(checkbox.checked){
                            fetch(`http://localhost:8080/api/visited?userId=1&countryId=${country.id}`,{
                                method: "POST"
                            })
                            visitedCountries.add(country.id);

                            //Add note and button
                            let input = document.createElement("input");
                            input.type = "text";
                            input.placeholder= "Add a note!";
                            input.id = `note-${country.id}`;
                            let saveButton = document.createElement("button");
                            saveButton.textContent = "Save";
                            saveButton.id = `save-${country.id}`;
                            saveButton.addEventListener("click", saveNote);
                            li.appendChild(input);
                            li.appendChild(saveButton);

                        }
                        else{
                            visitedCountries.delete(country.id);
                            fetch(`http://localhost:8080/api/visited?userId=1&countryId=${country.id}`,{
                                method: "DELETE"
                            })

                            // Remove the input and button
                            document.getElementById(`note-${country.id}`)?.remove();
                            document.getElementById(`save-${country.id}`)?.remove();
                        }
                    });

                    checkbox.checked = visitedCountries.has(country.id); //Making sure the boxes stay checked if we change search
                    li.appendChild(checkbox);
                    list.appendChild(li);
                }
                updatePageButtons(object);
            })
    }

    function urlBuilder(){
        let url = "http://localhost:8080/api/countries/"+ currentSorting;
        if(currentRegion !== "all"){
            url += "/" + currentRegion;
        }
        url+= "?page="+currentPage + "&descending="+descending;
        return url;
    }

    function updatePageButtons(object){
        document.getElementById('prevButton').disabled = object.first;
        document.getElementById('nextButton').disabled = object.last;
    }

    function saveNote(){

    }

})