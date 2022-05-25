/*MongoDB Scripts*/

use nms
db.createCollection("notification", { autoIndexId : true })

use pms
db.createCollection("payment", { autoIndexId : true })

use rms
db.createCollection("request", { autoIndexId : true })

use ams
db.createCollection("approval", { autoIndexId : true })
