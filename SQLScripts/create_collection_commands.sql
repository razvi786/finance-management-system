/*MongoDB Scripts*/

use fms
db.createCollection("payment", { autoIndexId : true })
db.createCollection("request", { autoIndexId : true })
db.createCollection("approval", { autoIndexId : true })
db.createCollection("notification", { autoIndexId : true })
