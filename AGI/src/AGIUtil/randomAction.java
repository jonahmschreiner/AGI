package AGIUtil;
import java.util.Random;
public class randomAction {
	public randomAction(){
		
	}
	
	public String RandomAction() {
		String output = "AGIUtil.Util util = new AGIUtil.Util();util.";
		Random rand = new Random();
		int selection = rand.nextInt(442);
		selection++;
	
		if (selection == 1) {
			output = "moveMouseLeft();";
		} else if (selection == 2) {
			output = "moveMouseRight();";
		} else if (selection == 3) {
			output = "moveMouseDown();";
		} else if (selection == 4) {
			output = "moveMouseUp();";
		} else if (selection == 5) {
			output = "leftMousePress();";
		} else if (selection == 6) {
			output = "leftMouseRelease();";
		} else if (selection == 7) {
			output = "rightMousePress();";
		} else if (selection == 8) {
			output = "rightMouseRelease();";
		} else if (selection == 9) {
			output = "middleMousePress();";
		} else if (selection == 10) {
			output = "middleMouseRelease();";
		} else if (selection == 11) {
			output = "scrollUp();";
		} else if (selection == 12) {
			output = "scrollDown();";
		} else if (selection == 13) {
			output = "pressEscape();";
		} else if (selection == 14) {
			output = "pressF1();";
		} else if (selection == 15) {
			output = "pressF2();";
		} else if (selection == 16) {
			output = "pressF3();";
		} else if (selection == 17) {
			output = "pressF4();";
		} else if (selection == 18) {
			output = "pressF5();";
		} else if (selection == 19) {
			output = "pressF6();";
		} else if (selection == 20) {
			output = "pressF7();";
		} else if (selection == 21) {
			output = "pressF8();";
		} else if (selection == 22) {
			output = "pressF9();";
		} else if (selection == 23) {
			output = "pressF10();";
		} else if (selection == 24) {
			output = "pressF11();";
		} else if (selection == 25) {
			output = "pressF12();";
		} else if (selection == 26) {
			output = "pressPrintScreen();";
		} else if (selection == 27) {
			output = "pressScrollLock();";
		} else if (selection == 28) {
			output = "pressPauseOrBreak();";
		} else if (selection == 29) {
			output = "pressRightQuote();";
		} else if (selection == 30) {
			output = "press1();";
		} else if (selection == 31) {
			output = "press2();";
		} else if (selection == 32) {
			output = "press3();";
		} else if (selection == 33) {
			output = "press4();";
		} else if (selection == 34) {
			output = "press5();";
		} else if (selection == 35) {
			output = "press6();";
		} else if (selection == 36) {
			output = "press7();";
		} else if (selection == 37) {
			output = "press8();";
		} else if (selection == 38) {
			output = "press9();";
		} else if (selection == 39) {
			output = "press0();";
		} else if (selection == 40) {
			output = "pressMinus();";
		} else if (selection == 41) {
			output = "pressEqual();";
		} else if (selection == 42) {
			output = "pressBackspace();";
		} else if (selection == 43) {
			output = "pressInsert();";
		} else if (selection == 44) {
			output = "pressHome();";
		} else if (selection == 45) {
			output = "pressPageUp();";
		} else if (selection == 46) {
			output = "pressTab();";
		} else if (selection == 47) {
			output = "pressQ();";
		} else if (selection == 48) {
			output = "pressW();";
		} else if (selection == 49) {
			output = "pressE();";
		} else if (selection == 50) {
			output = "pressR();";
		} else if (selection == 51) {
			output = "pressT();";
		} else if (selection == 52) {
			output = "pressY();";
		} else if (selection == 53) {
			output = "pressU();";
		} else if (selection == 54) {
			output = "pressI();";
		} else if (selection == 55) {
			output = "pressO();";
		} else if (selection == 56) {
			output = "pressP();";
		} else if (selection == 57) {
			output = "pressLeftBracket();";
		} else if (selection == 58) {
			output = "pressRightBracket();";
		} else if (selection == 59) {
			output = "pressBackslash();";
		} else if (selection == 60) {
			output = "pressDelete();";
		} else if (selection == 61) {
			output = "pressEnd();";
		} else if (selection == 62) {
			output = "pressPageDown();";
		} else if (selection == 63) {
			output = "pressA();";
		} else if (selection == 64) {
			output = "pressS();";
		} else if (selection == 65) {
			output = "pressD();";
		} else if (selection == 66) {
			output = "pressF();";
		} else if (selection == 67) {
			output = "pressG();";
		} else if (selection == 68) {
			output = "pressH();";
		} else if (selection == 69) {
			output = "pressJ();";
		} else if (selection == 70) {
			output = "pressK();";
		} else if (selection == 71) {
			output = "pressL();";
		} else if (selection == 72) {
			output = "pressSemicolon();";
		} else if (selection == 73) {
			output = "pressLeftQuote();";
		} else if (selection == 74) {
			output = "pressEnter();";
		} else if (selection == 75) {
			output = "pressShift();";
		} else if (selection == 76) {
			output = "pressZ();";
		} else if (selection == 77) {
			output = "pressX();";
		} else if (selection == 78) {
			output = "pressC();";
		} else if (selection == 79) {
			output = "pressV();";
		} else if (selection == 80) {
			output = "pressB();";
		} else if (selection == 81) {
			output = "pressN();";
		} else if (selection == 82) {
			output = "pressM();";
		} else if (selection == 83) {
			output = "pressComma();";
		} else if (selection == 84) {
			output = "pressPeriod();";
		} else if (selection == 85) {
			output = "pressSlash();";
		} else if (selection == 86) {
			output = "pressControl();";
		} else if (selection == 87) {
			output = "pressWindowsKey();";
		} else if (selection == 88) {
			output = "pressAlt();";
		} else if (selection == 89) {
			output = "pressSpace();";
		} else if (selection == 90) {
			output = "pressMenu();";
		} else if (selection == 91) {
			output = "pressUpArrow();";
		} else if (selection == 92) {
			output = "pressLeftArrow();";
		} else if (selection == 93) {
			output = "pressDownArrow();";
		} else if (selection == 94) {
			output = "pressRightArrow();";
		} else if (selection == 95) {
			output = "pressShiftRightQuote();";
		} else if (selection == 96) {
			output = "pressShift1();";
		} else if (selection == 97) {
			output = "pressShift2();";
		} else if (selection == 98) {
			output = "pressShift3();";
		} else if (selection == 99) {
			output = "pressShift4();";
		} else if (selection == 100) {
			output = "pressShift5();";
		} else if (selection == 101) {
			output = "pressShift6();";
		} else if (selection == 102) {
			output = "pressShift7();";
		} else if (selection == 103) {
			output = "pressShift8();";
		} else if (selection == 104) {
			output = "pressShift9();";
		} else if (selection == 105) {
			output = "pressShift0();";
		} else if (selection == 106) {
			output = "pressShiftMinus();";
		} else if (selection == 107) {
			output = "pressShiftEqual();";
		} else if (selection == 108) {
			output = "pressShiftQ();";
		} else if (selection == 109) {
			output = "pressShiftW();";
		} else if (selection == 110) {
			output = "pressShiftE();";
		} else if (selection == 111) {
			output = "pressShiftR();";
		} else if (selection == 112) {
			output = "pressShiftT();";
		} else if (selection == 113) {
			output = "pressShiftY();";
		} else if (selection == 114) {
			output = "pressShiftU();";
		} else if (selection == 115) {
			output = "pressShiftI();";
		} else if (selection == 116) {
			output = "pressShiftO();";
		} else if (selection == 117) {
			output = "pressShiftP();";
		} else if (selection == 118) {
			output = "pressShiftLeftBracket();";
		} else if (selection == 119) {
			output = "pressShiftRightBracket();";
		} else if (selection == 120) {
			output = "pressShiftBackslash();";
		} else if (selection == 121) {
			output = "pressShiftDelete();";
		} else if (selection == 122) {
			output = "pressShiftEnd();";
		} else if (selection == 123) {
			output = "pressShiftPageDown();";
		} else if (selection == 124) {
			output = "pressShiftA();";
		} else if (selection == 125) {
			output = "pressShiftS();";
		} else if (selection == 126) {
			output = "pressShiftD();";
		} else if (selection == 127) {
			output = "pressShiftF();";
		} else if (selection == 128) {
			output = "pressShiftG();";
		} else if (selection == 129) {
			output = "pressShiftH();";
		} else if (selection == 130) {
			output = "pressShiftJ();";
		} else if (selection == 131) {
			output = "pressShiftK();";
		} else if (selection == 132) {
			output = "pressShiftL();";
		} else if (selection == 133) {
			output = "pressShiftSemicolon();";
		} else if (selection == 134) {
			output = "pressShiftLeftQuote();";
		} else if (selection == 135) {
			output = "pressShiftEnter();";
		}  else if (selection == 136) {
			output = "pressShiftZ();";
		}  else if (selection == 137) {
			output = "pressShiftX();";
		}  else if (selection == 138) {
			output = "pressShiftC();";
		}  else if (selection == 139) {
			output = "pressShiftV();";
		}  else if (selection == 140) {
			output = "pressShiftB();";
		}  else if (selection == 141) {
			output = "pressShiftN();";
		}  else if (selection == 142) {
			output = "pressShiftM();";
		}  else if (selection == 143) {
			output = "pressShiftComma();";
		}  else if (selection == 144) {
			output = "pressShiftPeriod();";
		}  else if (selection == 145) {
			output = "pressShiftSlash();";
		}  else if (selection == 146) {
			output = "pressShiftUpArrow();";
		}  else if (selection == 147) {
			output = "pressShiftDownArrow();";
		}  else if (selection == 148) {
			output = "pressShiftLeftArrow();";
		}  else if (selection == 149) {
			output = "pressShiftRightArrow();";
		}  else if (selection == 150) {
			output = "pressShiftSpace();";
		}  else if (selection == 151) {
			output = "pressControlShiftRightQuote();";
		}  else if (selection == 152) {
			output = "pressControlShift1();";
		}  else if (selection == 153) {
			output = "pressControlShift2();";
		}  else if (selection == 154) {
			output = "pressControlShift3();";
		}  else if (selection == 155) {
			output = "pressControlShift4();";
		}  else if (selection == 156) {
			output = "pressControlShift5();";
		}  else if (selection == 157) {
			output = "pressControlShift6();";
		}  else if (selection == 158) {
			output = "pressControlShift7();";
		}  else if (selection == 159) {
			output = "pressControlShift8();";
		}  else if (selection == 160) {
			output = "pressControlShift9();";
		}  else if (selection == 161) {
			output = "pressControlShift0();";
		}  else if (selection == 162) {
			output = "pressControlShiftMinus();";
		}  else if (selection == 163) {
			output = "pressControlShiftEqual();";
		}  else if (selection == 164) {
			output = "pressControlShiftTab();";
		}  else if (selection == 165) {
			output = "pressControlShiftQ();";
		}  else if (selection == 166) {
			output = "pressControlShiftW();";
		}  else if (selection == 167) {
			output = "pressControlShiftE();";
		}  else if (selection == 168) {
			output = "pressControlShiftR();";
		}  else if (selection == 169) {
			output = "pressControlShiftT();";
		}  else if (selection == 170) {
			output = "pressControlShiftY();";
		}  else if (selection == 171) {
			output = "pressControlShiftU();";
		}  else if (selection == 172) {
			output = "pressControlShiftI();";
		}  else if (selection == 173) {
			output = "pressControlShiftO();";
		}  else if (selection == 174) {
			output = "pressControlShiftP();";
		}  else if (selection == 175) {
			output = "pressControlShiftLeftBracket();";
		}  else if (selection == 176) {
			output = "pressControlShiftRightBracket();";
		}  else if (selection == 177) {
			output = "pressControlShiftBackslash();";
		}  else if (selection == 178) {
			output = "pressControlShiftDelete();";
		}  else if (selection == 179) {
			output = "pressControlShiftEnd();";
		}  else if (selection == 180) {
			output = "pressControlShiftPageDown();";
		}  else if (selection == 181) {
			output = "pressControlShiftA();";
		}  else if (selection == 182) {
			output = "pressControlShiftS();";
		}  else if (selection == 183) {
			output = "pressControlShiftD();";
		}  else if (selection == 184) {
			output = "pressControlShiftF();";
		}  else if (selection == 185) {
			output = "pressControlShiftG();";
		}  else if (selection == 186) {
			output = "pressControlShiftH();";
		}  else if (selection == 187) {
			output = "pressControlShiftJ();";
		}  else if (selection == 188) {
			output = "pressControlShiftK();";
		}  else if (selection == 189) {
			output = "pressControlShiftL();";
		}  else if (selection == 190) {
			output = "pressControlShiftSemicolon();";
		}  else if (selection == 191) {
			output = "pressControlShiftLeftQuote();";
		}  else if (selection == 192) {
			output = "pressControlShiftEnter();";
		}  else if (selection == 193) {
			output = "pressControlShiftZ();";
		}  else if (selection == 194) {
			output = "pressControlShiftX();";
		}  else if (selection == 195) {
			output = "pressControlShiftC();";
		}  else if (selection == 196) {
			output = "pressControlShiftV();";
		}  else if (selection == 197) {
			output = "pressControlShiftB();";
		}  else if (selection == 198) {
			output = "pressControlShiftN();";
		}  else if (selection == 199) {
			output = "pressControlShiftM();";
		}  else if (selection == 200) {
			output = "pressControlShiftComma();";
		}  else if (selection == 201) {
			output = "pressControlShiftPeriod();";
		}  else if (selection == 202) {
			output = "pressControlShiftSlash();";
		}  else if (selection == 203) {
			output = "pressControlShiftUpArrow();";
		}  else if (selection == 204) {
			output = "pressControlShiftDownArrow();";
		}  else if (selection == 205) {
			output = "pressControlShiftRightArrow();";
		}  else if (selection == 206) {
			output = "pressControlShiftLeftArrow();";
		}  else if (selection == 207) {
			output = "pressControlShiftSpace();";
		}  else if (selection == 208) {
			output = "pressControlRightQuote();";
		}  else if (selection == 209) {
			output = "pressControl1();";
		}  else if (selection == 210) {
			output = "pressControl2();";
		}  else if (selection == 211) {
			output = "pressControl3();";
		}  else if (selection == 212) {
			output = "pressControl4();";
		}  else if (selection == 213) {
			output = "pressControl5();";
		}  else if (selection == 214) {
			output = "pressControl6();";
		}  else if (selection == 215) {
			output = "pressControl7();";
		}  else if (selection == 216) {
			output = "pressControl8();";
		}  else if (selection == 217) {
			output = "pressControl9();";
		}  else if (selection == 218) {
			output = "pressControl0();";
		}  else if (selection == 219) {
			output = "pressControlMinus();";
		}  else if (selection == 220) {
			output = "pressControlEqual();";
		}  else if (selection == 221) {
			output = "pressControlTab();";
		}  else if (selection == 222) {
			output = "pressControlQ();";
		}  else if (selection == 223) {
			output = "pressControlW();";
		}  else if (selection == 224) {
			output = "pressControlE();";
		}   else if (selection == 225) {
			output = "pressControlR();";
		}   else if (selection == 226) {
			output = "pressControlT();";
		}   else if (selection == 227) {
			output = "pressControlY();";
		}   else if (selection == 228) {
			output = "pressControlU();";
		}   else if (selection == 229) {
			output = "pressControlI();";
		}   else if (selection == 230) {
			output = "pressControlO();";
		}   else if (selection == 231) {
			output = "pressControlP();";
		}   else if (selection == 232) {
			output = "pressControlLeftBracket();";
		}   else if (selection == 233) {
			output = "pressControlRightBracket();";
		}   else if (selection == 234) {
			output = "pressControlBackslash();";
		}   else if (selection == 235) {
			output = "pressControlDelete();";
		}   else if (selection == 236) {
			output = "pressControlEnd();";
		}   else if (selection == 237) {
			output = "pressControlPageDown();";
		}   else if (selection == 238) {
			output = "pressControlA();";
		}   else if (selection == 239) {
			output = "pressControlS();";
		}   else if (selection == 240) {
			output = "pressControlD();";
		}   else if (selection == 241) {
			output = "pressControlF();";
		}   else if (selection == 242) {
			output = "pressControlG();";
		}   else if (selection == 243) {
			output = "pressControlH();";
		}   else if (selection == 244) {
			output = "pressControlJ();";
		}   else if (selection == 245) {
			output = "pressControlK();";
		}   else if (selection == 246) {
			output = "pressControlL();";
		}   else if (selection == 247) {
			output = "pressControlSemicolon();";
		}   else if (selection == 248) {
			output = "pressControlLeftQuote();";
		}   else if (selection == 249) {
			output = "pressControlEnter();";
		}   else if (selection == 250) {
			output = "pressControlZ();";
		}   else if (selection == 251) {
			output = "pressControlX();";
		}   else if (selection == 252) {
			output = "pressControlC();";
		}   else if (selection == 253) {
			output = "pressControlV();";
		}   else if (selection == 254) {
			output = "pressControlB();";
		}   else if (selection == 255) {
			output = "pressControlN();";
		}   else if (selection == 256) {
			output = "pressControlM();";
		}   else if (selection == 257) {
			output = "pressControlComma();";
		}    else if (selection == 258) {
			output = "pressControlPeriod();";
		}    else if (selection == 259) {
			output = "pressControlSlash();";
		}    else if (selection == 260) {
			output = "pressControlUpArrow();";
		}    else if (selection == 261) {
			output = "pressControlDownArrow();";
		}    else if (selection == 262) {
			output = "pressControlLeftArrow();";
		}    else if (selection == 263) {
			output = "pressControlSpace();";
		}    else if (selection == 264) {
			output = "pressAltRightQuote();";
		}    else if (selection == 265) {
			output = "pressAlt1();";
		}    else if (selection == 266) {
			output = "pressAlt2();";
		}    else if (selection == 267) {
			output = "pressAlt3();";
		}    else if (selection == 268) {
			output = "pressAlt4();";
		}    else if (selection == 269) {
			output = "pressAlt5();";
		}    else if (selection == 270) {
			output = "pressAlt6();";
		}    else if (selection == 271) {
			output = "pressAlt7();";
		}    else if (selection == 272) {
			output = "pressAlt8();";
		}    else if (selection == 273) {
			output = "pressAlt9();";
		}    else if (selection == 274) {
			output = "pressAlt0();";
		}    else if (selection == 275) {
			output = "pressAltMinus();";
		}    else if (selection == 276) {
			output = "pressAltEqual();";
		}    else if (selection == 277) {
			output = "pressAltTab();";
		}    else if (selection == 278) {
			output = "pressAltQ();";
		}    else if (selection == 279) {
			output = "pressAltW();";
		}    else if (selection == 280) {
			output = "pressAltE();";
		}    else if (selection == 281) {
			output = "pressAltR();";
		}    else if (selection == 282) {
			output = "pressAltT();";
		}    else if (selection == 283) {
			output = "pressAltY();";
		}    else if (selection == 284) {
			output = "pressAltU();";
		}    else if (selection == 285) {
			output = "pressAltI();";
		}    else if (selection == 286) {
			output = "pressAltO();";
		}    else if (selection == 287) {
			output = "pressAltP();";
		} else if (selection == 288) {
			output = "pressAltLeftBracket();";
		} else if (selection == 289) {
			output = "pressAltRightBracket();";
		} else if (selection == 290) {
			output = "pressAltBackslash();";
		} else if (selection == 291) {
			output = "pressAltDelete();";
		} else if (selection == 292) {
			output = "pressAltEnd();";
		} else if (selection == 293) {
			output = "pressAltPageDown();";
		} else if (selection == 294) {
			output = "pressAltA();";
		} else if (selection == 295) {
			output = "pressAltS();";
		} else if (selection == 296) {
			output = "pressAltD();";
		} else if (selection == 297) {
			output = "pressAltF();";
		} else if (selection == 298) {
			output = "pressAltG();";
		} else if (selection == 299) {
			output = "pressAltH();";
		} else if (selection == 300) {
			output = "pressAltJ();";
		} else if (selection == 301) {
			output = "pressAltK();";
		} else if (selection == 302) {
			output = "pressAltL();";
		} else if (selection == 303) {
			output = "pressAltSemicolon();";
		} else if (selection == 304) {
			output = "pressAltLeftQuote();";
		} else if (selection == 305) {
			output = "pressAltEnter();";
		} else if (selection == 306) {
			output = "pressAltZ();";
		} else if (selection == 307) {
			output = "pressAltX();";
		} else if (selection == 308) {
			output = "pressAltC();";
		} else if (selection == 309) {
			output = "pressAltV();";
		} else if (selection == 310) {
			output = "pressAltB();";
		} else if (selection == 311) {
			output = "pressAltN();";
		} else if (selection == 312) {
			output = "pressAltM();";
		} else if (selection == 313) {
			output = "pressAltComma();";
		} else if (selection == 314) {
			output = "pressAltPeriod();";
		} else if (selection == 315) {
			output = "pressAltSlash();";
		} else if (selection == 316) {
			output = "pressAltUpArrow();";
		} else if (selection == 317) {
			output = "pressAltDownArrow();";
		} else if (selection == 318) {
			output = "pressAltLeftArrow();";
		} else if (selection == 319) {
			output = "pressAltRightArrow();";
		} else if (selection == 320) {
			output = "pressAltSpace();";
		}    else if (selection == 321) {
			output = "pressControlAltRightQuote();";
		}    else if (selection == 322) {
			output = "pressControlAlt1();";
		}    else if (selection == 323) {
			output = "pressControlAlt2();";
		}    else if (selection == 324) {
			output = "pressControlAlt3();";
		}    else if (selection == 325) {
			output = "pressControlAlt4();";
		}    else if (selection == 326) {
			output = "pressControlAlt5();";
		}    else if (selection == 327) {
			output = "pressControlAlt6();";
		}    else if (selection == 328) {
			output = "pressControlAlt7();";
		}    else if (selection == 329) {
			output = "pressControlAlt8();";
		}    else if (selection == 330) {
			output = "pressControlAlt9();";
		}    else if (selection == 331) {
			output = "pressControlAlt0();";
		}    else if (selection == 332) {
			output = "pressControlAltMinus();";
		}    else if (selection == 333) {
			output = "pressControlAltEqual();";
		}    else if (selection == 334) {
			output = "pressControlAltTab();";
		}    else if (selection == 335) {
			output = "pressControlAltQ();";
		}    else if (selection == 336) {
			output = "pressControlAltW();";
		}    else if (selection == 337) {
			output = "pressControlAltE();";
		}    else if (selection == 338) {
			output = "pressControlAltR();";
		}    else if (selection == 339) {
			output = "pressControlAltT();";
		}    else if (selection == 340) {
			output = "pressControlAltY();";
		}    else if (selection == 341) {
			output = "pressControlAltU();";
		}    else if (selection == 342) {
			output = "pressControlAltI();";
		}    else if (selection == 343) {
			output = "pressControlAltO();";
		}    else if (selection == 344) {
			output = "pressControlAltP();";
		} else if (selection == 345) {
			output = "pressControlAltLeftBracket();";
		} else if (selection == 346) {
			output = "pressControlAltRightBracket();";
		} else if (selection == 347) {
			output = "pressControlAltBackslash();";
		} else if (selection == 348) {
			output = "pressControlAltDelete();";
		} else if (selection == 349) {
			output = "pressControlAltEnd();";
		} else if (selection == 350) {
			output = "pressControlAltPageDown();";
		} else if (selection == 351) {
			output = "pressControlAltA();";
		} else if (selection == 352) {
			output = "pressControlAltS();";
		} else if (selection == 353) {
			output = "pressControlAltD();";
		} else if (selection == 354) {
			output = "pressControlAltF();";
		} else if (selection == 355) {
			output = "pressControlAltG();";
		} else if (selection == 356) {
			output = "pressControlAltH();";
		} else if (selection == 357) {
			output = "pressControlAltJ();";
		} else if (selection == 358) {
			output = "pressControlAltK();";
		} else if (selection == 359) {
			output = "pressControlAltL();";
		} else if (selection == 360) {
			output = "pressControlAltSemicolon();";
		} else if (selection == 361) {
			output = "pressControlAltLeftQuote();";
		} else if (selection == 362) {
			output = "pressControlAltEnter();";
		} else if (selection == 363) {
			output = "pressControlAltZ();";
		} else if (selection == 364) {
			output = "pressControlAltX();";
		} else if (selection == 365) {
			output = "pressControlAltC();";
		} else if (selection == 366) {
			output = "pressControlAltV();";
		} else if (selection == 367) {
			output = "pressControlAltB();";
		} else if (selection == 368) {
			output = "pressControlAltN();";
		} else if (selection == 369) {
			output = "pressControlAltM();";
		} else if (selection == 370) {
			output = "pressControlAltComma();";
		} else if (selection == 371) {
			output = "pressControlAltPeriod();";
		} else if (selection == 372) {
			output = "pressControlAltSlash();";
		} else if (selection == 373) {
			output = "pressControlAltUpArrow();";
		} else if (selection == 374) {
			output = "pressControlAltDownArrow();";
		} else if (selection == 375) {
			output = "pressControlAltLeftArrow();";
		} else if (selection == 376) {
			output = "pressControlAltRightArrow();";
		} else if (selection == 377) {
			output = "pressControlAltSpace();";
		} else if (selection == 378) {
			output = "pressControlEscape();";
		} else if (selection == 379) {
			output = "pressControlF1();";
		} else if (selection == 380) {
			output = "pressControlF2();";
		} else if (selection == 381) {
			output = "pressControlF3();";
		} else if (selection == 382) {
			output = "pressControlF4();";
		} else if (selection == 383) {
			output = "pressControlF5();";
		} else if (selection == 384) {
			output = "pressControlF6();";
		} else if (selection == 385) {
			output = "pressControlF7();";
		} else if (selection == 386) {
			output = "pressControlF8();";
		} else if (selection == 387) {
			output = "pressControlF9();";
		} else if (selection == 388) {
			output = "pressControlF10();";
		} else if (selection == 389) {
			output = "pressControlF11();";
		} else if (selection == 390) {
			output = "pressControlF12();";
		} else if (selection == 391) {
			output = "pressShiftEscape();";
		} else if (selection == 392) {
			output = "pressShiftF1();";
		} else if (selection == 393) {
			output = "pressShiftF2();";
		} else if (selection == 394) {
			output = "pressShiftF3();";
		} else if (selection == 395) {
			output = "pressShiftF4();";
		} else if (selection == 396) {
			output = "pressShiftF5();";
		} else if (selection == 397) {
			output = "pressShiftF6();";
		} else if (selection == 398) {
			output = "pressShiftF7();";
		} else if (selection == 399) {
			output = "pressShiftF8();";
		} else if (selection == 400) {
			output = "pressShiftF9();";
		} else if (selection == 401) {
			output = "pressShiftF10();";
		} else if (selection == 402) {
			output = "pressShiftF11();";
		} else if (selection == 403) {
			output = "pressShiftF12();";
		} else if (selection == 404) {
			output = "pressAltF1();";
		} else if (selection == 405) {
			output = "pressAltF2();";
		} else if (selection == 406) {
			output = "pressAltF3();";
		} else if (selection == 407) {
			output = "pressAltF4();";
		} else if (selection == 408) {
			output = "pressAltF5();";
		} else if (selection == 409) {
			output = "pressAltF6();";
		} else if (selection == 410) {
			output = "pressAltF7();";
		} else if (selection == 411) {
			output = "pressAltF8();";
		} else if (selection == 412) {
			output = "pressAltF9();";
		} else if (selection == 413) {
			output = "pressAltF10();";
		} else if (selection == 414) {
			output = "pressAltF11();";
		} else if (selection == 415) {
			output = "pressAltF12();";
		} else if (selection == 416) {
			output = "pressControlShiftEscape();";
		} else if (selection == 417) {
			output = "pressControlShiftF1();";
		} else if (selection == 418) {
			output = "pressControlShiftF2();";
		} else if (selection == 419) {
			output = "pressControlShiftF3();";
		} else if (selection == 420) {
			output = "pressControlShiftF4();";
		} else if (selection == 421) {
			output = "pressControlShiftF5();";
		} else if (selection == 422) {
			output = "pressControlShiftF6();";
		} else if (selection == 423) {
			output = "pressControlShiftF7();";
		} else if (selection == 424) {
			output = "pressControlShiftF8();";
		} else if (selection == 425) {
			output = "pressControlShiftF9();";
		} else if (selection == 426) {
			output = "pressControlShiftF10();";
		} else if (selection == 427) {
			output = "pressControlShiftF11();";
		} else if (selection == 428) {
			output = "pressControlShiftF12();";
		} else if (selection == 429) {
			output = "pressControlAltEscape();";
		} else if (selection == 430) {
			output = "pressControlAltF1();";
		} else if (selection == 431) {
			output = "pressControlAltF2();";
		} else if (selection == 432) {
			output = "pressControlAltF3();";
		} else if (selection == 433) {
			output = "pressControlAltF4();";
		} else if (selection == 434) {
			output = "pressControlAltF5();";
		} else if (selection == 435) {
			output = "pressControlAltF6();";
		} else if (selection == 436) {
			output = "pressControlAltF7();";
		} else if (selection == 437) {
			output = "pressControlAltF8();";
		} else if (selection == 438) {
			output = "pressControlAltF9();";
		} else if (selection == 439) {
			output = "pressControlAltF10();";
		} else if (selection == 440) {
			output = "pressControlAltF11();";
		} else if (selection == 441) {
			output = "pressControlAltF12();";
		}	
		
		return output;
	}
}
