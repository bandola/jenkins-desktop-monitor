int led = 10;
boolean blinking = false;

void setup() {               
  Serial.begin(9600); 
  pinMode(led, OUTPUT);
}

void loop() {
  processSerialInput();
  
  if(blinking == true){
    blinkCurrentLed();
  }
}

void processSerialInput(){
  if (Serial.available() > 0) {
    int inByte = Serial.read();
    if(inByte == '1'){
     blinking = true;
    }else{
      blinking = false;
    }
  }
}

void blinkCurrentLed() {
   digitalWrite(led, HIGH);
   delay(500);
   digitalWrite(led, LOW);
   delay(500);
}
