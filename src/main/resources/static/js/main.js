
document.addEventListener("DOMContentLoaded", function(){
    let currentPage= 0; //Keeping track on what page we are using.
    let currentSorting = "name";
    let currentFilter ="all";
    let descending = false;
    let visitedCountries = new Set(); //Since mocked user, the user has no visited countries when the app start.

    //Initialize in case site is refreshed.
    initializeVisitedCountries().then(renderCountries);

    document.getElementById("filter").addEventListener("change",function (){
        currentFilter = this.value;
        currentPage= 0;
        renderCountries();
    })


    document.getElementById('nameButton').addEventListener("click", function(){
        currentSorting = "name";
        currentPage= 0;
        renderCountries();
    })

    document.getElementById('popButton').addEventListener('click', function(){
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
                    li.textContent = country.name + " | Pop.: " + country.population + " | Visited:";

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
                            createNotes(country.id,li)
                        }
                        else{
                            visitedCountries.delete(country.id);
                            fetch(`http://localhost:8080/api/visited?userId=1&countryId=${country.id}`,{
                                method: "DELETE"
                            })

                            // Remove the input and button
                            document.getElementById(`note-${country.id}`)?.remove();
                            document.getElementById(`save-${country.id}`)?.remove();
                            document.getElementById(`delete-${country.id}`)?.remove();
                        }
                    });

                    li.appendChild(checkbox);
                    if(visitedCountries.has(country.id)){
                        checkbox.checked = true; //Making sure the boxes stay checked if we change search
                        createNotes(country.id, li);
                    }

                    list.appendChild(li);
                }
                updatePageButtons(object);
            })
    }

    function urlBuilder(){
        if(currentFilter === "Visited"){
            return `http://localhost:8080/api/visited/1?page=${currentPage}&size=25&descending=${descending}`; //Hardcoded userId.
        }
        let url = "http://localhost:8080/api/countries/"+ currentSorting;
        if(currentFilter !== "all"){
            url += "/" + currentFilter;
        }
        url+= "?page="+currentPage + "&descending="+descending;
        return url;
    }

    function updatePageButtons(object){
        document.getElementById('prevButton').disabled = object.first;
        document.getElementById('nextButton').disabled = object.last;
    }

    function createNotes(countryId, li){
        let input = document.createElement("input");
        input.type = "text";
        input.placeholder= "Add a note!";
        input.id = `note-${countryId}`;
        if(visitedCountries.has(countryId)){
            getNote(countryId,1).then(function (note){
                input.value=note;
            })
        }
        let saveButton = document.createElement("button");
        saveButton.textContent = "Save";
        saveButton.id = `save-${countryId}`;
        saveButton.addEventListener("click", function(){
            updateNote(countryId, input.value)
        });
        let deleteButton =document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.id = `delete-${countryId}`;
        deleteButton.addEventListener("click",function (){
            updateNote(countryId, ""); //Empty entry for the note!
            input.value = "";
        });
        li.appendChild(input);
        li.appendChild(saveButton);
        li.appendChild(deleteButton);
    }

    function updateNote(countryId, note){
        fetch(`http://localhost:8080/api/visited?userId=1&countryId=${countryId}&note=${note}`, {
            method: "PATCH"
        })
    }

    function getNote(countryId, userId){
        return fetch(`http://localhost:8080/api/visited/${userId}/${countryId}`)
            .then(function(response){
                return response.text();
            });
    }

    function initializeVisitedCountries(){
        return fetch("http://localhost:8080/api/visited/1?page=0&size=25")
            .then(response => response.json())
            .then(function(object){
                visitedCountries.clear();
                for (let country of object.content){
                    visitedCountries.add(country.id);
                }
            })
            .catch(function(error){
                console.error("Failed to load visited countries:", error);
            });
    }

})