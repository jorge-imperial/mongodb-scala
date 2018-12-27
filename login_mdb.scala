import com.mongodb.async.client.MongoClients
import com.mongodb.{ ConnectionString}
import org.mongodb.scala._

object login_mdb extends App {

  val url: String = "mongodb+srv://admin:XXXXXX@cluster0-puqrj.mongodb.net/admin?streamType=netty&retryWrites=true"

  val mongoClient = MongoClient(MongoClients.create(new ConnectionString(url)))

  val database: MongoDatabase = mongoClient.getDatabase("test")
  val collection: MongoCollection[Document] = database.getCollection("foo")
    .withCodecRegistry(MongoClient.DEFAULT_CODEC_REGISTRY)

  val document: Document = Document("_id" -> 1, "x" -> 1)
  val insertObservable: Observable[Completed] = collection.insertOne(document)

  insertObservable.subscribe(new Observer[Completed] {
    override def onNext(result: Completed): Unit = println(s"onNext: $result")
    override def onError(e: Throwable): Unit = println(s"onError: $e")
    override def onComplete(): Unit = println("onComplete")
  })

}
