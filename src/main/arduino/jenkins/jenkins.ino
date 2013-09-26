#include "pitches.h"
#include "mario.h"

int red = 13;
int green = 12;
int yellow = 11;
int buzzer = 10;

int currentLed = -1;
boolean blinking = false;

void setup() {               
  Serial.begin(9600); 
  pinMode(red, OUTPUT);
  pinMode(green, OUTPUT);
  pinMode(yellow, OUTPUT);
  pinMode(buzzer, OUTPUT);
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

    switch (inByte) {
    case 'r':    
      turnOnSingleLed(red);
      break;
    case 'g':    
      turnOnSingleLed(green);
      break;
    case 'y':    
      turnOnSingleLed(yellow);
      break;
    case 'm':    
      playImperialMarch();
    case 'M':    
      playSong(MARI0_MAIN_THEME, MARI0_MAIN_THEME_TEMPO, sizeof(MARI0_MAIN_THEME)); 
    case 'X':    
      playSong(MARIO_UNDERWORLD, MARIO_UNDERWORLD_TEMPO, sizeof(MARIO_UNDERWORLD)); 
      break;
    case 'b':    
      blinking = true;
      break;
    case 'o':
      turnOffAllLeds();
      break;
    } 
  }
}

void turnOffAllLeds(){
  for(int i = 11; i <= 13; i++){
    digitalWrite(i, LOW);
  } 
}

void blinkCurrentLed(){
  digitalWrite(currentLed, LOW);
  delay(500);
  digitalWrite(currentLed, HIGH);
  delay(500);
}
void turnOnSingleLed(int led){
  turnOffAllLeds();
  blinking = false;
  currentLed = led;
  digitalWrite(led, HIGH);
}

void beep (int frequencyInHertz, long timeInMilliseconds){ 
    int x; 	 
    long delayAmount = (long)(1000000/frequencyInHertz);
    long loopTime = (long)((timeInMilliseconds*1000)/(delayAmount*2));
    for (x=0;x<loopTime;x++) 	 
    { 	 
        digitalWrite(buzzer,HIGH);
        delayMicroseconds(delayAmount);
        digitalWrite(buzzer,LOW);
        delayMicroseconds(delayAmount);
    } 	 
  
    delay(20);
    //a little delay to make all notes sound separate
} 	 
  	 
void playImperialMarch()
{ 	 
   //Adaptado de https://gist.github.com/tagliati/1804108
    beep(NOTE_A4, 500); 
    beep(NOTE_A4, 500);     
    beep(NOTE_A4, 500); 
    beep(NOTE_F4, 350); 
    beep(NOTE_C5, 150);
    
    beep(NOTE_A4, 500);
    beep(NOTE_F4, 350);
    beep(NOTE_C5, 150);
    beep(NOTE_A4, 1000);
       
    beep(NOTE_E5, 500);
    beep(NOTE_E5, 500);
    beep(NOTE_E5, 500);    
    beep(NOTE_F5, 350); 
    beep(NOTE_C5, 150);
    
    beep(NOTE_GS4, 500);
    beep(NOTE_F4, 350);
    beep(NOTE_C5, 150);
    beep(NOTE_A4, 1000);
   
}

void playSong(int melody[], int tempo[], int size){
  int newSize = size / sizeof(int);
  for (int thisNote = 0; thisNote < newSize; thisNote++) {
    int noteDuration = 1000/tempo[thisNote];
    tone(10, melody[thisNote],noteDuration);
    int pauseBetweenNotes = noteDuration * 1.30;
    delay(pauseBetweenNotes);
    noTone(10);
  }
}





