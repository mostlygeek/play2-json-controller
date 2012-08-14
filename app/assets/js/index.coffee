$ -> 
    count = 0
    out = (content) ->
        count++ 
        $('#count').text(count)
        $('#output').html(content)

    $('#sendName').on "click", -> 
        p = $.ajax 
            type: "POST"
            url: "/echoName"
            contentType : "application/json"
            processData: false
            data: JSON.stringify
                name: "mostlygeek"

        p.success (resp) -> 
            out(resp)
    
