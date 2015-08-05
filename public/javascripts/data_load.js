/**
 * Created by jclausman on 5/12/14.
 */

function table_copytest(tableElem, notification_id) {
    jsRoutes.controllers.CopyTests.list(notification_id).ajax({
        success: function (data) {
            tableElem.handsontable({
                data: data['copyTests'],
                startRows: 1,
                startCols: 8,
                minSpareRows: 1,
                colHeaders: ['ID', 'Message', 'URL', 'send limit', 'enabled?', 'start', 'end'],
                columns: [
                    { data: 'id', type: 'numeric', readOnly: true },
                    { data: 'message'},
                    { data: 'url'},
                    { data: 'sendLimit', type: 'numeric' },
                    { data: 'enabled', type: 'checkbox' },
                    { data: 'start', type: 'date', dateFormat: 'yy.mm.dd' },
                    { data: 'end', type: 'date', dateFormat: 'yy.mm.dd' }
                ],
                afterChange: function (changes, source) {
                    console.log("change:", changes, "  source:", source);
                    if (source == "loadData") {
                        return;
                    }
                    changes.map(function (change) {
                        var rowId = change[0];
                        var table = tableElem.handsontable('getInstance');
                        var testId = table.getDataAtCell(rowId, 0);
                        var jsonChange = {
                            notifId: notification_id,
                            // Get the testId for this row
                            testId: testId,
                            key: change[1],
                            newValue: change[3]
                        };
                        // The ID is updated via server response, so don't bother doing it here
                        if (jsonChange.key == "id") {
                            return;
                        }
                        jsRoutes.controllers.CopyTests.save().ajax({
                            type: "POST",
                            contentType: 'application/json',
                            processData: false,
                            data: JSON.stringify(jsonChange),
                            success: function (data) {
                                if (data != testId) {
                                    table.setDataAtCell(rowId, 0, data);
                                }
                                console.log("Successfully saved test # " + testId);
                            }
                        });
                    });
                }
            });
        }
    });
}