var summaryStatus = new Map();

function detailClicked(element) {
    if(element.id) {
        summaryStatus.set(element.id, element.open ? "" : "open");
    }
}

function start_project(project) {
    var url = "/api/project-processor/start";
    if(undefined != project) {
        url = url + "?name=" + project;
    }
    $.ajax({
      url  : url,
      type : "GET"
    }).done(function(data, statusText, xhr){
        if(xhr.status == 200) {
            var content = drawData(data);
            $("#b_table").html(content);
            $("#error_message").html("");
            if(undefined == project) {
                $("#b_stop").show();
                $("#b_refresh").show();
                $("#b_start").hide();
            }
        } else {
            $("#error_message").html("ERR: " + statusText);
        }
    });
}

function stop_project(project) {
    var url = "/api/project-processor/stop";
    if(undefined != project) {
        url = url + "?name=" + project;
    }
    $.ajax({
      url  : url,
      type : "GET"
    }).done(function(data, statusText, xhr){
        if(xhr.status == 200) {
            var content = drawData(data);
            $("#b_table").html(content);
            $("#error_message").html("");
            if(undefined == project) {
                $("#b_stop").hide();
                $("#b_refresh").hide();
                $("#b_start").show();
            }
        } else {
            $("#error_message").html("ERR: " + statusText);
        }
    });
}

function refresh_data() {
    $.ajax({
      url  : "/api/project-processor",
      type : "GET"
    }).done(function(data, statusText, xhr){
        if(xhr.status == 200) {
            var content = drawData(data);
            $("#b_table").html(content);
            $("#error_message").html("");
        } else {
            $("#error_message").html("ERR: " + statusText);
        }
    });
}

function findSummaryStatus(name) {
    if(undefined != name && undefined != summaryStatus.get(name)) {
        return summaryStatus.get(name);
    } else {
        return '';
    }
}

function drawData(data) {
    var content = "";
    content = content + "<tr>";
    content = content + "<th>Name</th>";
    content = content + "<th>Status</th>";
    content = content + "<th>Tasks</th>";
    content = content + "<th>Others</th>";
    content = content + "<th>Action</th>";
    content = content + "</tr>";

    if(undefined != data && null != data) {
        if(undefined != data.projects && null != data.projects && data.projects.length > 0) {
            for(var item in data.projects) {
                var project = data.projects[item];
                content = content + "<tr>";
                content = content + "<td class=\"col_1\">";
                //Column: Project Name
                content = content + project.name;

                content = content + "</td>";
                content = content + "<td class=\"col_2\">";
                //Column: Status
                content = content + "Status: " + project.status + "<br/>";
                content = content + "Ignore: " + project.ignore + "<br/>";
                content = content + "Processing: " + project.processing + "<br/>";
                content = content + "Processed: " + project.processPercentage + "%<br/>";
                content = content + "Exception: " + project.exception + "<br/>";
                content = content + "Force Stop: " + project.forceStop + "<br/>";
                content = content + "Completed: " + project.completed + "<br/>";

                content = content + "</td>";
                content = content + "<td class=\"col_3\">";
                //Column: Tasks
                content = content + "<details id=\"" + "task_" + project.name + "\" " + findSummaryStatus("task_" + project.name) + " onclick=\"detailClicked(this)\">";
                content = content + "<summary>Tasks</summary>";
                for(var tItem in project.tasks) {
                    var task = project.tasks[tItem];
                    content = content + "<div class=\"task_box\">";
                    content = content + "Name: " + task.name + "<br/>";
                    content = content + "Order: " + task.order + "<br/>";
                    content = content + "Status: " + task.status + "<br/>";
                    content = content + "Ignore: " + task.ignore + "<br/>";
                    content = content + "Completed: " + task.completed + "<br/>";
                    content = content + "Exception: " + task.exception + "<br/>";
                    content = content + "Exception Detail: " + task.exceptionDetail + "<br/>";
                    content = content + "Start Time: " + task.startTime + "<br/>";
                    content = content + "Finish Time: " + task.finishTime + "<br/>";
                    content = content + "Retry: " + task.taskRetry + "<br/>";

                    content = content + "</div>";
                }
                content = content + "</details>";

                content = content + "</td>";
                content = content + "<td class=\"col_4\">";
                //Column: Others
                content = content + "<details id=\"" + "other_" + project.name + "\" " + findSummaryStatus("other_" + project.name) + " onclick=\"detailClicked(this)\">";
                content = content + "<summary>Project Detail</summary>";
                content = content + "<div class=\"task_box\">";
                content = content + "URL: " + project.url + "<br/>";
                content = content + "Dependencies: " + JSON.stringify(project.dependencies) + "<br/>";
                content = content + "Exception Detail: " + project.exceptionDetail + "<br/>";
                content = content + "Finish Time: " + project.finishTime + "<br/>";
                content = content + "Local Dir: " + project.localDir + "<br/>";
                content = content + "Order: " + project.order + "<br/>";
                content = content + "Repo URL: " + project.repoUrl + "<br/>";
                content = content + "RunId: " + project.runId + "<br/>";
                content = content + "Source: " + project.source + "<br/>";
                content = content + "Start Time: " + project.startTime + "<br/>";
                content = content + "Target: " + project.target + "<br/>";
                content = content + "Retry: " + project.taskRetry + "<br/>";
                content = content + "Work: " + project.work + "<br/>";

                content = content + "</div>";
                content = content + "</details>";

                content = content + "</td>";
                content = content + "<td class=\"col_5\">";
                //Column: Action
                if(project.completed == false && project.forceStop == false && project.exception == false && project.ignore == false) {
                    content = content + "<button id=\"b_stop_" + project.name + "\" onclick=\"stop_project('" + project.name + "');\">" + "Stop</button>";
                }
                if(project.processing == false && (project.forceStop == true || project.exception == true || project.ignore == true)) {
                    content = content + "<button id=\"b_start_" + project.name + "\" onclick=\"start_project('" + project.name + "');\">" + "Start</button>";
                }

                content = content + "</td>";
                content = content + "</tr>";

                if(project.processing == true) {
                    $("#b_stop").show();
                    $("#b_refresh").show();
                    $("#b_start").hide();
                }
            }
        }
    }
    return content;
}

(function($) {
    $(document).ready(function(){
        $("#b_stop").hide();
        $("#b_refresh").hide();
        refresh_data();

        $("#b_start").click(function(){
          start_project(undefined);
        });

        $("#b_stop").click(function(){
          stop_project(undefined);
        });

        $("#b_refresh").click(function(){
            refresh_data();
        });

        setInterval(function() {
            if($("#b_stop").is(":hidden")) {
                return;
            }
            refresh_data();
        }, 2000);
    });
})(jQuery);


