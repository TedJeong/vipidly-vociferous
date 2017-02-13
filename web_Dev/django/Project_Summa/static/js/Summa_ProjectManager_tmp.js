/**
 * Created by joo on 17. 2. 12.
 */

    // Controller panel 에 Categories and workspace table 을 선언합니다.
    $(document).ready(function() {
        $('#ctr1-catogory-workspace-table').DataTable();
    } );

    // Controller panel 에 ctr1-task-table1 을 선언합니다.
    $(document).ready(function() {
        var table = $('#ctr1-task-table1').DataTable( {
            columnDefs: [ {
                orderable: false,
                className: 'select-checkbox',
                targets:   0
            } ],
            select: {
                style:    'multi',
                selector: 'td:first-child'
            },
            order: [[ 1, 'asc' ]]
        } );

    $('.delete_yes_button').on('click', function(){
        // JQuery DataTable api 를 이용하지 않고 task.pk 값을 받아옵니다.
        /*
        var table = $(this).closest('div > .task-table-wrapper')
        var selected_row = $('.btn-task-delete').parent().siblings().children().find('tr.selected');
        var selected_task_pk = selected_row.find('td.task-pk');
        var task_pk_array = [];
        for(var i=0;i<selected_task_pk.length;i++){
            console.log(selected_task_pk[i].innerHTML);
            task_pk_array.push(selected_task_pk[i].innerHTML);
        }
        */

        // DataTable 에서 task.pk 값을 받아옵니다.
        var task_pks = $.map(table.rows('.selected').data(), function (item) {
            return item[1]
        });
        console.log(task_pks);
        //alert(table.rows('.selected').data().length + ' row(s) selected');


        // 선택된 row들을 테이블에서 지웁니다. (ajaxSetup 에 넣으면 실행 안됩니다.)
        table.rows('.selected').remove().draw( false );

        // 여러 table의 ajax call 을 구분해주는 구분자를 넣어 줍니다.
        var ajax_delete_differer = $(this).val();
        //alert('ajax_delete_differer : '+ajax_delete_differer);

        var csrftoken = $.cookie('csrftoken');

        function csrfSafeMethod(method) {
            // these HTTP methods do not require CSRF protection
            return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
        }

        $.ajaxSetup({
            beforeSend: function(xhr, settings) {
                if (!csrfSafeMethod(settings.type) && !this.crossDomain) {
                    xhr.setRequestHeader("X-CSRFToken", csrftoken);
                }

            }
        });

        // delete_task should come from delete_yes_button name and value but it's not working right now.
        $.ajax({
            type: 'POST',
            url: '/projectmanager/',
            data: {'task_pks[]': task_pks, 'ajax_differer': ajax_delete_differer, 'delete_task': 1},
            success: function(data){
               //alert('DB deleted! and ' + data);
            },
        });
    });





    } );

    // Controller panel 에 ctr1-contribution-ranker-table 을 선언합니다.
    $(document).ready(function() {
    $('#ctr1-contribution-ranker-table').DataTable( {
        "scrollY":        "200px",
        "scrollCollapse": true,
        "paging":         false
    } );
    } );

    // Controller panel 에서 view category modal 로부터 cateo
    $(document).ready(function(){
        var ajax_differer = 2;

        var csrftoken = $.cookie('csrftoken');

        function csrfSafeMethod(method) {
            // these HTTP methods do not require CSRF protection
            return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
        }

        $.ajaxSetup({
            beforeSend: function(xhr, settings) {
                if (!csrfSafeMethod(settings.type) && !this.crossDomain) {
                    xhr.setRequestHeader("X-CSRFToken", csrftoken);
                }

            }
        });


        $.ajax({
            type: 'POST',
            url: '/projectmanager/',
            data: {'ajax_differer': ajax_differer},
            success: function(data){
               alert('DB deleted! and ' + data);
            },
        });
    });


