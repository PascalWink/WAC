function loadCountries(){
	fetch("restservices/countries")
	.then(response => response.json())
	.then(function(countrieCodes){
		var landen = document.getElementById("opties");
		for (let countrieCode of countrieCodes){
			fetch("restservices/countries/" + countrieCode.naam)
			.then(response => response.json())
			.then(function(countrie){
				var tr = document.createElement('TR');
				var latitude = countrie.lat;
				var longitude = countrie.lng;
				var city = countrie.capital;
				tr.setAttribute('class', 'crosshair')
				tr.setAttribute('onclick', 'showWeather(' + latitude + ', ' + longitude + ' , "' + city + '")')
		        // land
			    var td1 = document.createElement('TD')
		        td1.appendChild(document.createTextNode(countrie.naam));
		        tr.appendChild(td1)
		        // hoofdstad
			    var td2 = document.createElement('TD')
		        td2.appendChild(document.createTextNode(countrie.capital));
		        tr.appendChild(td2)
		        // regio
			    var td3 = document.createElement('TD')
		        td3.appendChild(document.createTextNode(countrie.region));
		        tr.appendChild(td3)
		        // oppervlakte
			    var td4 = document.createElement('TD')
		        td4.appendChild(document.createTextNode(countrie.surface));
		        tr.appendChild(td4)
		        // inwoners
			    var td5 = document.createElement('TD')				        
		        td5.appendChild(document.createTextNode(countrie.population));
		        tr.appendChild(td5)
		        
		       	var td6 = document.createElement('TD')	
		       	var wijzig = document.createElement("BUTTON");
		        wijzig.innerHTML = "wijzig";
		        wijzig.addEventListener('click',function(){								
					document.getElementById('landcode').innerHTML = countrie.code;
		        	PUTlandForm.style.visibility='visible';

		        });
		        td6.appendChild(wijzig);
		        tr.appendChild(td6)
		        
		        var td7 = document.createElement('TD')
		        var verwijder = document.createElement("BUTTON");
		        verwijder.innerHTML = "verwijder";
		        verwijder.addEventListener('click',function(){
		        	var id = countrie.code;
		        	console.log(countrie.code+"verwijder");
	    			let fetchoptions = {
	    					method: 'DELETE',
	    					headers: {
	    						'Authorization': 'Bearer ' + window.sessionStorage.getItem("sessionToken")
	    					}
	    				}
		        	fetch("restservices/countries/"+id, fetchoptions)
		        		.then(function (response){
		        			if(response.ok){
		        				var table = document. getElementById("countrieTable");
		        				for(var i = table.rows.length; i > -1;i--){
		        					table.deleteRow(i -1);
		        				}
		        				console.log("countrie verwijdert");
		        				loadCountries();
		        			} else if (response.status == 401) {
		        				console.log("niet ingelogd");
		        			} else {
		        				console.log("kan niet verwijderen")
		        			}
		        		})
		       	});
		        
		        td7.appendChild(verwijder);
		        tr.appendChild(td7)
		        //DAS
			    countrieTable.appendChild(tr);
			});		        
		}
	});
}

function showWeather(latitude, longitude, city){
	var date = new Date();
	var tijd = date.getTime();
	var tienMinGeleden = tijd - 10*60000;
	var oldWeather = JSON.parse(window.localStorage.getItem(city))
	var nieuw = 0;
	if(oldWeather != null){
		if(oldWeather.tijd < tienMinGeleden){
			nieuw = 1;
			console.log("langer");
		} else{
			console.log("korter");
		}
	} else{
		nieuw = 1;
	}	
	if(nieuw == 0){
		console.log("oud");
		document.getElementById('stad').innerHTML = city;
		document.getElementById('tempratuur').innerHTML = "Tempratuur: " + oldWeather.tempratuur;			
		document.getElementById('luchtvochtigheid').innerHTML = "Luchtvochtigheid: " + oldWeather.luchtvochtigheid;
		document.getElementById('windsnelheid').innerHTML = "Windsnelheid: " + oldWeather.windsnelheid;
		document.getElementById('windrichting').innerHTML = "Windrichting: " + oldWeather.windrichting;
		document.getElementById('zonsopgang').innerHTML = "Zonsondergang: " + oldWeather.zonsopgang;
		document.getElementById('zonsondergang').innerHTML = "Zonsondergang: " + oldWeather.zonsondergang;
	} else{				
		fetch("http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&appid=4ca3622a4ced82d4bcef3cf02763cd09")
		.then(response => response.json())
		.then(function(weatherJson){
			var windrichting = "Noord"; 
			var deg = weatherJson.wind.deg;
			if(deg >= 1 && deg >= 90){
				windrichting = "Oost";
			} if(deg >= 91 && deg >= 180){
				windrichting = "Oost";
			} if(deg >= 181 && deg >= 270){
				windrichting = "Zuid";
			} if(deg >= 271 && deg >= 360 || deg == 0){
				windrichting = "Noord";
			}
			// unix timestamp naar normale tijd
			var date = new Date(weatherJson.sys.sunrise*1000);
			var hours = date.getHours();
			var minutes = "0" + date.getMinutes();
			var seconds = "0" + date.getSeconds();
			var zonsopgang = hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
			date = new Date(weatherJson.sys.sunset*1000);
			hours = date.getHours();
			minutes = "0" + date.getMinutes();
			seconds = "0" + date.getSeconds();
			zonsondergang = hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
			
			var weather = {"tempratuur" : weatherJson.main.temp, "luchtvochtigheid" : weatherJson.main.humidity, "windsnelheid" : weatherJson.wind.speed, "windrichting" : windrichting, "zonsopgang" : zonsopgang, "zonsondergang" : zonsondergang, "tijd" :  tijd};
			window.localStorage.setItem(city, JSON.stringify(weather)); 
			
			document.getElementById('stad').innerHTML = city;
			document.getElementById('tempratuur').innerHTML = "Tempratuur: " + weatherJson.main.temp;			
			document.getElementById('luchtvochtigheid').innerHTML = "Luchtvochtigheid: " + weatherJson.main.humidity;
			document.getElementById('windsnelheid').innerHTML = "Windsnelheid: " + weatherJson.wind.speed;
			document.getElementById('windrichting').innerHTML = "Windrichting: " + windrichting;
			document.getElementById('zonsopgang').innerHTML = "Zonsondergang: " + zonsopgang;
			document.getElementById('zonsondergang').innerHTML = "Zonsondergang: " + zonsondergang;
		});	
	}
}

function initPage() {
	fetch("https://ipapi.co/json/")
		.then(response => response.json())
		.then(function(myJson){
			document.getElementById('city').innerHTML = "city: " +  myJson.city;
			document.getElementById('region').innerHTML = "region: " + myJson.region;
			document.getElementById('region-code').innerHTML = "Region-code" + myJson.region_code;
			document.getElementById('country').innerHTML = "Country: " + myJson.country;
			document.getElementById('land-naam').innerHTML = "Land naam: " + myJson.country_name;
			document.getElementById('latitude').innerHTML = "Latitude: " +  myJson.latitude;
			document.getElementById('longitude').innerHTML = "Longitude: " + myJson.longitude;
			showWeather(myJson.latitude, myJson.longitude, myJson.city);
			loadCountries();
		});
}; 

document.getElementById("put").addEventListener("click", function(){
	var id = document.querySelector("#put_id").value;
	var formData = new FormData(document.querySelector("#PUTlandForm"));
	formData.append("namePut", document.getElementById("namePut").value);
	var encData = new URLSearchParams(formData.entries());
	let fetchoptions = {
			method: 'PUT',
			body: encData,
			headers: {
				'Authorization': 'Bearer ' + window.sessionStorage.getItem("sessionToken")
			}
		}
	fetch("restservices/countries/"+id, fetchoptions)
	.then(function(response){
		if(response.ok){
			console.log('saved')
		} 
		else{
			alert("niet ingelogd");
		}
	})
	var table = document. getElementById("countrieTable");
	for(var i = table.rows.length; i > -1;i--){
		table.deleteRow(i -1);
	}
	loadCountries();
});

document.getElementById("add").addEventListener("click", function(){
	var formData = new FormData(document.querySelector("#ADDlandForm"));
	var encData = new URLSearchParams(formData.entries());
	let fetchoptions = {
			method: 'POST',
			body: encData,
			headers: {
				'Authorization': 'Bearer ' + window.sessionStorage.getItem("sessionToken")
			}
		}
	fetch("restservices/countries", fetchoptions)
	.then(function(response){
		if(response.ok){
			alert("opgeslagen");
		} 
		else if(response.status == 400){
			alert("landCode bestaat al")
		} else{
			alert("je bent niet ingelogd");
		}
	})
	var table = document. getElementById("countrieTable");
	for(var i = table.rows.length; i > -1;i--){
		table.deleteRow(i -1);
	}
	loadCountries();
});

document.getElementById("login").addEventListener("click", function(){
	var formData = new FormData(document.querySelector("#loginForm"));
	var encData = new URLSearchParams(formData.entries());
	fetch("restservices/authentication", {method : 'POST', body : encData})
	.then(function(response){
		if(response.ok){
			return response.json()
		} else {
			alert("inloggen niet gelukt");
		};
	})
	.then(function(myJson){
		window.sessionStorage.setItem("sessionToken", myJson.JWT);
		alert("ingelogd met token : " + window.sessionStorage.getItem("sessionToken"));
	})
});