'use strict';

describe('exportCtrl', function() {

    var factory, serviceUtility, controller, exportCtrl, document = [];
    var object1, object2, key;

    document[0] = {
            URL: '/#/topics',
            location: {
                href: '/#/topics'
            }
        };

    beforeEach(module('eightyFactories'));
    beforeEach(module('eightyControllers'));

    beforeEach(inject(function (questionsFactory, utility, $rootScope, $controller) {
        key = 'exportSet';
        object1 = {id: 1, object: 'obj1'};
        object2 = {id: 2, object: 'obj2'};
        controller = $controller;

        factory = questionsFactory;
        serviceUtility = utility;
        serviceUtility.eraseSet(key);
        serviceUtility.saveInSet(key, object1);
        serviceUtility.saveInSet(key, object2);
    }));

    it("check export", function () {
        exportCtrl = controller('exportCtrl', {$document: document});
        expect(exportCtrl.checkExport(key)).toBe(true);
        expect(exportCtrl.exportLen).toBe(2);
        serviceUtility.eraseSet(key);
    });

    it("clear export if url doesn't include #/export", function () {
        exportCtrl = controller('exportCtrl', {$document: document});
        exportCtrl.clearList();
        expect(document[0].location.href).toBe('/#/topics');
        expect(exportCtrl.checkExport(key)).toBe(false);
        expect(exportCtrl.exportLen).toBe(0);
    });

    it("clear export if url includes /#/export", function () {
        document[0] = {
                URL: 'localhost/#/export',
                location: {
                    href: 'localhost/#/export'
                }
            };
        exportCtrl = controller('exportCtrl', {$document: document});
        exportCtrl.clearList();
        expect(document[0].location.href).toBe('localhost/#/home');
        expect(exportCtrl.checkExport(key)).toBe(false);
        expect(exportCtrl.exportLen).toBe(0);
    });

});

