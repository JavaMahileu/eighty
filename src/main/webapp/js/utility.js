'use strict';
/*exported isPrintLog, getIndex, containsInSet, replaceDOT, printLog, errorAlert */
/**
 * Created by Aliaksandr_Padalka on 25/08/2014.
 */

function getIndex(set, ob) {
    var index = 0;
    for (var i = 0; i < set.length; i++) {
        if (set[i].id === ob.id) {
            index = i;
        }
    }
    return index;
}

function containsInSet(set, ob) {
    var flag = false;
    for (var i = 0; i < set.length; i++) {
        if (set[i].id === ob.id) {
            flag = true;
        }
    }
    return flag;
}

function replaceDOT(query) {
    query = query.replace('.', '|');
    return query;
}

function printLog(msg) {
    console.log(msg);
}

function errorAlert(msg, $modal) {
    $modal.open({
        template: '<div class="modal-header" style="background:red;"><h3 class="modal-title">Eror</h3></div>' +
            '<div class="modal-body"><h3 class="modal-title">' + msg + '</h3></div>' +
            '<div class="modal-footer"><button class="btn btn-primary delete-topic" data-ng-click="ok()">Ok</button></div>',
        controller: function($scope, $modalInstance) {
            $scope.ok = function() {
                $modalInstance.close($scope.topic);
            };
        },
        size: 'sm'
    });
}
