// This function is called when the entire window (including images and scripts) has finished loading
window.onload = function () {

    searchParams = new URLSearchParams(window.location.search);
    id = searchParams.get('id')
    xhttp = new XMLHttpRequest()

    if (id != null && id != "") {
        // ask to endpoint spring the xml file path
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/getImpianto/" + id,
            success: function (response) {
                console.log("Risposta dal server:", response)
                xmlFileName = response.path
                console.log(xmlFileName)
                if (xmlFileName != null && xmlFileName != "") {
                    // open the xml file path
                    xhttp.open("GET", xmlFileName, true)
                    xhttp.send()
                }
                else {
                    mostraErrore("Errore nel percorso del file xml: " + xmlFileName)
                }
            },
            error: function (xhr, status, error) {
                if (xhr.status == 404) {
                    mostraErrore("L'impianto non è attivo.")
                }
                console.error("Errore durante la richiesta AJAX:", error)
            }
        })
    }
    else {
        mostraErrore('Il parametro id richiesto non è presente.')
    }

    // Define a function to handle the response from the XMLHttpRequest
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            if (this.status == 200) {
                // If the request is successful (status 200), process the XML response
                caricaCartelloni(this);
            } else {
                // other errors
                mostraErrore('Errore durante il caricamento del file XML: ' + this)
            }
        }
    }
}

function mostraErrore(messaggio) {
    svg_errore = `
        <svg height="32" style="overflow:visible;enable-background:new 0 0 32 32" viewBox="0 0 32 32" width="32" xml:space="preserve" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
            <g>
                <g id="Error_1_">
                    <g id="Error">
                        <circle cx="16" cy="16" id="BG" r="16" style="fill:#D72828;"/>
                        <path d="M14.5,25h3v-3h-3V25z M14.5,6v13h3V6H14.5z" id="Exclamatory_x5F_Sign" style="fill:#E6E6E6;"/>
                    </g>
                </g>
            </g>
        </svg>`
    errore = $('<p>').append(svg_errore).append(messaggio).css({ 'color': 'red', 'font-size':'21px', 'text-shadow': '0 4px 8px rgba(0, 0, 0, 0.2)' })
    $(errore).css({ 'text-align': 'center', 'padding': '20px' })
    $(errore).children('p svg').css({ 'padding-right': '10px', 'vertical-align': 'middle' })

    $('#main').append(errore)
    $('body').css({ 'background': 'grey' })
}

function setImpiantoStaus(id_impianto, id_palinsesto, cartellone) {
    id_cartellone = parseInt(cartellone.getAttribute("id"))
    durata = parseFloat(cartellone.getElementsByTagName("durata")[0].childNodes[0].nodeValue)

    console.log("id_impianto: ", id_impianto, ", id_palinsesto: ", id_palinsesto, ", id_cartellone: ", id_cartellone, ", durata", durata)
    $.ajax({
        type: "POST",
        url: "http://localhost:8000/MonitoraggioImpianti_war_exploded/setImpiantoStatus",
        data: {
            id_impianto: id_impianto,
            id_palinsesto: id_palinsesto,
            id_cartellone: id_cartellone,
            durata: durata
        },
        success: function (response) {
            console.log("Risposta dal server:", response)
        },
        error: function (xhr, status, error) {
            console.error("Errore durante la richiesta AJAX:", error)
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
