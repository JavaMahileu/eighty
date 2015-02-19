'use strict';

describe("addTopicInstanceController", function () {

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

        controller = $controller("addTopicInstanceCtrl", {
            $scope: scope
        });

    }));

    it("cancel create topic click", function () {
        controller.cancelTopicCreation();
        expect(scope.$dismiss).toHaveBeenCalled();
    });

    it("create topic click when a title is not null", function () {
        controller.topic = {title: 'fake topic'};
        controller.createTopic();
        expect(scope.$close).toHaveBeenCalledWith(controller.topic);
    });

    it("create topic click when a title is null", function () {
        controller.createTopic();
        expect(scope.$close).not.toHaveBeenCalled();
    });
});

