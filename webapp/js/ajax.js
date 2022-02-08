
    $(function() {

    $('#acInput').autocomplete({
        source: 'executor?command=hotels&subcommand=ajax'
    })
});

    $(function() {

    $('#resort-input').autocomplete({
        source: 'executor?command=resorts&subcommand=ajax'
    })
});

    $(function() {

    $('#location-input').autocomplete({
        source: 'executor?command=location_ajax'
    })
});
