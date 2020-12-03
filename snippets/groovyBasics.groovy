def looseVar = "string" // can be any type. true/false, 1337, 0.2321 etc.
int var = 42

if (var >= 0) {
  //do things
} else {
  //do other things
}

def func(String aString, Boolean aBool) {
  if (aBool) {
    return aString
  } else {
    return false
  }
}

def res = func (looseVar, true) // res is now "string"

String str = "One may use \$ sign to include variable in a ${looseVar} like this: $var, or like this: ${var}"
// 'str' now equals to "One may use $ sign to include variable in a string like this: 42, or like this: 42"
String otherOne = 'Single qotes do not allow $interpolation, every symbol would be stored as is'

def list = [true, "two", 3]
def biggerList = list.add(4.0) // adds 4.0 to the end of list
int listLength = list.size() // three elements inn the list, thus - 3
def element1 = list[0]

def aMap = ["key": "value", "anotherKey": 42]
def valueOfKey = aMap["key"]
def anotherValue = aMap.anotherKey

for (int i=0; i<=10; i++){
  var++
}

for (i in list) {
  println(i)
}

biggerList.each{
  println(it)
}

aMap.each{ k, v ->
  println("key: $k, value: $v")
}

def get = new URL("https://google.com").openConnection()
def getRC = get.getResponseCode()
if(getRC.equals(200)) {
    println(get.getInputStream().getText())
}
// https://stackoverflow.com/questions/25692515/groovy-built-in-rest-http-client

try {
  def out
  def get = new URL("https://google.com").openConnection()
  def getRC = get.getResponseCode()
  if(getRC.equals(200)) {
      out = get.getInputStream().getText()
  }
} catch (err) {
  throw new Exception('Couldn\'t reach google: ' + err)
}