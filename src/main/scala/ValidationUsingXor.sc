import cats.data.Xor

case class Data(email: String, phone: String)

def validateEmail(email: String): Xor[List[String], String] = {
  val emailRegex = """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r
  email match{
    case e if emailRegex.findFirstMatchIn(e).isDefined  => Xor.Right(e)
    case _                                              => Xor.Left(List("Invalid email format"))
  }
}

def validatePhone(phone: String): Xor[List[String], String] = {
  val phoneRegex = """^\+(?:[0-9] ?){6,14}[0-9]$""".r
  phone match{
    case p if phoneRegex.findFirstMatchIn(p).isDefined  => Xor.Right(p)
    case _                                              => Xor.Left(List("Phone number must be numeric"))
  }
}

def validateData(d: Data): Xor[List[String], Data] =
  for {
    validEmail <- validateEmail(d.email)
    validPhone <- validatePhone(d.phone)
  } yield Data(validEmail, validPhone)

/** *** ***/

val okEmail = "email@email.com"
val badEmail = "email.com"

val okPhone = "+1 1234567890123"
val badPhone = "not-a-valid-phone"

validateData(Data(okEmail, okPhone))
validateData(Data(badEmail, badPhone))
validateData(Data(okEmail, badPhone))
validateData(Data(badEmail, okPhone))
