// This function is called when the entire window (including images and scripts) has finished loading
window.onload = function () {
    xhttp = new XMLHttpRequest()
    // Get current HTML file name
    currentPage = window.location.pathname.split("/").pop()
    // Set xml file based on current HTML
    xmlFileName = currentPage.replace(".html", ".xml")
    // Open a GET request to retrieve the xml file based on current html
    xhttp.open("GET", xmlFileName, true)
    xhttp.send()

    // Define a function to handle the response from the XMLHttpRequest
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // If the request is successful (status 200), process the XML response
            caricaCartelloni(this)
        }
    }
}

function setImpiantoStaus(id_impianto, id_palinsesto, cartellone) {
    id_cartellone = parseInt(cartellone.getAttribute("id"))
    durata = parseFloat(cartellone.getElementsByTagName("durata")[0].childNodes[0].nodeValue)

    console.log(id_impianto, id_palinsesto, id_cartellone, durata)
    $.ajax({
        type: "POST",
        url: "http://localhost:8000/MonitoraggioImpianti_war_exploded/setImpiantoStatus",
        data: {
            id_impianto: id_impianto,
            id_palinsesto: id_palinsesto,
            id_cartellone: id_cartellone,
            durata: durata
        },
        success: function(response) {
            console.log("Risposta dal server:", response)
            // Gestisci la risposta dal server qui
        },
        error: function(xhr, status, error) {
            console.error("Errore durante la richiesta AJAX:", error)
            // Gestisci l'errore qui
        }
    })
}

function caricaCartelloni(xml) {
    // Parse the XML response into a DOM object
    xmlDoc = xml.responseXML

    // Start the cartellone rotation from the first 
    index = 0
    id_impianto = parseInt(xmlDoc.getElementsByTagName("impianto")[0].getAttribute("id"))
    id_palinsesto = parseInt(xmlDoc.getElementsByTagName("palinsesto")[0].getAttribute("id"))

    // Get all "cartellone" elements from the parsed XML document
    cartelloni = xmlDoc.getElementsByTagName("cartellone")

    // shows cartelloni recursively
    function mostraProssimoCartellone() {
        // get current cartellone
        cartellone = cartelloni[index]

        // shows cartellone 
        source_url = cartellone.getElementsByTagName("source")[0].childNodes[0].nodeValue
        caricaContenutoHTML(source_url)

        // update database
        setImpiantoStaus(id_impianto, id_palinsesto, cartellone)

        // increment index to load next cartellone
        index++
        // if index goes over n of cartelloni, goes back to start
        if (index >= cartelloni.length) {
            index = 0
        }

        // set timeout to show next cartellone
        setTimeout(mostraProssimoCartellone, parseInt(cartellone.getElementsByTagName("durata")[0].childNodes[0].nodeValue) * 1000)
    }

    // Starts cartelloni visualization
    mostraProssimoCartellone()
}

// This function loads the HTML content from the provided URL and replaces the content of the "main" container
function caricaContenutoHTML(url) {
    // "main" container in HTML doc
    main_container = document.getElementById("main")

    xhr = new XMLHttpRequest()
    xhr.open('GET', url, true)

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            main_container.innerHTML = xhr.responseText
        }
    }

    xhr.send()
}
