'use strict';

describe("deleteTopicInstanceController", function () {

    var scope, controller;

    beforeEach(module('eightyFactories'));
    beforeEach(module('eightyControllers'));

    beforeEach(inject(function ($controller) {

        scope = {
            $close: function (result) {
            },
            $dismiss: function () {
            }
        };

        spyOn(scope, "$close");
        spyOn(scope, "$dismiss");

        controller = $controller("deleteTopicInstanceCtrl", {
            $scope: scope
        });

    }));

    it("cancel delete topic click", function () {
        controller.cancelDeleteTopic();
        expect(scope.$dismiss).toHaveBeenCalled();
    });

    it("delete topic click", function () {
        controller.topic = {title: 'fake topic'};
        controller.deleteTopic();
        expect(scope.$close).toHaveBeenCalledWith(controller.topic);
    });
});

