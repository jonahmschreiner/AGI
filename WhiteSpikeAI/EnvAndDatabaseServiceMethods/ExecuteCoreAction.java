package EnvAndDatabaseServiceMethods;

public class ExecuteCoreAction {
	public static void exec(int coreActionIndex) {
		Util util = new Util();
		if (coreActionIndex == 1) {
			util.moveMouseLeft();
		} else if (coreActionIndex == 2) {
			util.moveMouseRight();
		} else if (coreActionIndex == 3) {
			util.moveMouseDown();
		} else if (coreActionIndex == 4) {
			util.moveMouseUp();
		} else if (coreActionIndex == 5) {
			util.leftMousePress();
		} else if (coreActionIndex == 6) {
			util.leftMouseRelease();
		} else if (coreActionIndex == 7) {
			util.rightMousePress();
		} else if (coreActionIndex == 8) {
			util.rightMouseRelease();
		} else if (coreActionIndex == 9) {
			util.middleMousePress();
		} else if (coreActionIndex == 10) {
			util.middleMouseRelease();
		} else if (coreActionIndex == 11) {
			util.scrollUp();
		} else if (coreActionIndex == 12) {
			util.scrollDown();
		} else if (coreActionIndex == 13) {
			util.pressEscape();
		} else if (coreActionIndex == 14) {
			util.pressF1();
		} else if (coreActionIndex == 15) {
			util.pressF2();
		} else if (coreActionIndex == 16) {
			util.pressF3();
		} else if (coreActionIndex == 17) {
			util.pressF4();
		} else if (coreActionIndex == 18) {
			util.pressF5();
		} else if (coreActionIndex == 19) {
			util.pressF6();
		} else if (coreActionIndex == 20) {
			util.pressF7();
		} else if (coreActionIndex == 21) {
			util.pressF8();
		} else if (coreActionIndex == 22) {
			util.pressF9();
		} else if (coreActionIndex == 23) {
			util.pressF10();
		} else if (coreActionIndex == 24) {
			util.pressF11();
		} else if (coreActionIndex == 25) {
			util.pressF12();
		} else if (coreActionIndex == 26) {
			util.pressPrintScreen();
		} else if (coreActionIndex == 27) {
			util.pressScrollLock();
		} else if (coreActionIndex == 28) {
			util.pressPauseOrBreak();
		} else if (coreActionIndex == 29) {
			util.pressRightQuote();
		} else if (coreActionIndex == 30) {
			util.press1();
		} else if (coreActionIndex == 31) {
			util.press2();
		} else if (coreActionIndex == 32) {
			util.press3();
		} else if (coreActionIndex == 33) {
			util.press4();
		} else if (coreActionIndex == 34) {
			util.press5();
		} else if (coreActionIndex == 35) {
			util.press6();
		} else if (coreActionIndex == 36) {
			util.press7();
		} else if (coreActionIndex == 37) {
			util.press8();
		} else if (coreActionIndex == 38) {
			util.press9();
		} else if (coreActionIndex == 39) {
			util.press0();
		} else if (coreActionIndex == 40) {
			util.pressMinus();
		} else if (coreActionIndex == 41) {
			util.pressEqual();
		} else if (coreActionIndex == 42) {
			util.pressBackspace();
		} else if (coreActionIndex == 43) {
			util.pressInsert();
		} else if (coreActionIndex == 44) {
			util.pressHome();
		} else if (coreActionIndex == 45) {
			util.pressPageUp();
		} else if (coreActionIndex == 46) {
			util.pressTab();
		} else if (coreActionIndex == 47) {
			util.pressQ();
		} else if (coreActionIndex == 48) {
			util.pressW();
		} else if (coreActionIndex == 49) {
			util.pressE();
		} else if (coreActionIndex == 50) {
			util.pressR();
		} else if (coreActionIndex == 51) {
			util.pressT();
		} else if (coreActionIndex == 52) {
			util.pressY();
		} else if (coreActionIndex == 53) {
			util.pressU();
		} else if (coreActionIndex == 54) {
			util.pressI();
		} else if (coreActionIndex == 55) {
			util.pressO();
		} else if (coreActionIndex == 56) {
			util.pressP();
		} else if (coreActionIndex == 57) {
			util.pressLeftBracket();
		} else if (coreActionIndex == 58) {
			util.pressRightBracket();
		} else if (coreActionIndex == 59) {
			util.pressBackslash();
		} else if (coreActionIndex == 60) {
			util.pressDelete();
		} else if (coreActionIndex == 61) {
			util.pressEnd();
		} else if (coreActionIndex == 62) {
			util.pressPageDown();
		} else if (coreActionIndex == 63) {
			util.pressA();
		} else if (coreActionIndex == 64) {
			util.pressS();
		} else if (coreActionIndex == 65) {
			util.pressD();
		} else if (coreActionIndex == 66) {
			util.pressF();
		} else if (coreActionIndex == 67) {
			util.pressG();
		} else if (coreActionIndex == 68) {
			util.pressH();
		} else if (coreActionIndex == 69) {
			util.pressJ();
		} else if (coreActionIndex == 70) {
			util.pressK();
		} else if (coreActionIndex == 71) {
			util.pressL();
		} else if (coreActionIndex == 72) {
			util.pressSemicolon();
		} else if (coreActionIndex == 73) {
			util.pressLeftQuote();
		} else if (coreActionIndex == 74) {
			util.pressEnter();
		} else if (coreActionIndex == 75) {
			util.pressShift();
		} else if (coreActionIndex == 76) {
			util.pressZ();
		} else if (coreActionIndex == 77) {
			util.pressX();
		} else if (coreActionIndex == 78) {
			util.pressC();
		} else if (coreActionIndex == 79) {
			util.pressV();
		} else if (coreActionIndex == 80) {
			util.pressB();
		} else if (coreActionIndex == 81) {
			util.pressN();
		} else if (coreActionIndex == 82) {
			util.pressM();
		} else if (coreActionIndex == 83) {
			util.pressComma();
		} else if (coreActionIndex == 84) {
			util.pressPeriod();
		} else if (coreActionIndex == 85) {
			util.pressSlash();
		} else if (coreActionIndex == 86) {
			util.pressControl();
		} else if (coreActionIndex == 87) {
			util.pressWindowsKey();
		} else if (coreActionIndex == 88) {
			util.pressAlt();
		} else if (coreActionIndex == 89) {
			util.pressSpace();
		} else if (coreActionIndex == 90) {
			util.pressMenu();
		} else if (coreActionIndex == 91) {
			util.pressUpArrow();
		} else if (coreActionIndex == 92) {
			util.pressLeftArrow();
		} else if (coreActionIndex == 93) {
			util.pressDownArrow();
		} else if (coreActionIndex == 94) {
			util.pressRightArrow();
		} else if (coreActionIndex == 95) {
			util.pressShiftRightQuote();
		} else if (coreActionIndex == 96) {
			util.pressShift1();
		} else if (coreActionIndex == 97) {
			util.pressShift2();
		} else if (coreActionIndex == 98) {
			util.pressShift3();
		} else if (coreActionIndex == 99) {
			util.pressShift4();
		} else if (coreActionIndex == 100) {
			util.pressShift5();
		} else if (coreActionIndex == 101) {
			util.pressShift6();
		} else if (coreActionIndex == 102) {
			util.pressShift7();
		} else if (coreActionIndex == 103) {
			util.pressShift8();
		} else if (coreActionIndex == 104) {
			util.pressShift9();
		} else if (coreActionIndex == 105) {
			util.pressShift0();
		} else if (coreActionIndex == 106) {
			util.pressShiftMinus();
		} else if (coreActionIndex == 107) {
			util.pressShiftEqual();
		} else if (coreActionIndex == 108) {
			util.pressShiftQ();
		} else if (coreActionIndex == 109) {
			util.pressShiftW();
		} else if (coreActionIndex == 110) {
			util.pressShiftE();
		} else if (coreActionIndex == 111) {
			util.pressShiftR();
		} else if (coreActionIndex == 112) {
			util.pressShiftT();
		} else if (coreActionIndex == 113) {
			util.pressShiftY();
		} else if (coreActionIndex == 114) {
			util.pressShiftU();
		} else if (coreActionIndex == 115) {
			util.pressShiftI();
		} else if (coreActionIndex == 116) {
			util.pressShiftO();
		} else if (coreActionIndex == 117) {
			util.pressShiftP();
		} else if (coreActionIndex == 118) {
			util.pressShiftLeftBracket();
		} else if (coreActionIndex == 119) {
			util.pressShiftRightBracket();
		} else if (coreActionIndex == 120) {
			util.pressShiftBackslash();
		} else if (coreActionIndex == 121) {
			util.pressShiftDelete();
		} else if (coreActionIndex == 122) {
			util.pressShiftEnd();
		} else if (coreActionIndex == 123) {
			util.pressShiftPageDown();
		} else if (coreActionIndex == 124) {
			util.pressShiftA();
		} else if (coreActionIndex == 125) {
			util.pressShiftS();
		} else if (coreActionIndex == 126) {
			util.pressShiftD();
		} else if (coreActionIndex == 127) {
			util.pressShiftF();
		} else if (coreActionIndex == 128) {
			util.pressShiftG();
		} else if (coreActionIndex == 129) {
			util.pressShiftH();
		} else if (coreActionIndex == 130) {
			util.pressShiftJ();
		} else if (coreActionIndex == 131) {
			util.pressShiftK();
		} else if (coreActionIndex == 132) {
			util.pressShiftL();
		} else if (coreActionIndex == 133) {
			util.pressShiftSemicolon();
		} else if (coreActionIndex == 134) {
			util.pressShiftLeftQuote();
		} else if (coreActionIndex == 135) {
			util.pressShiftEnter();
		}  else if (coreActionIndex == 136) {
			util.pressShiftZ();
		}  else if (coreActionIndex == 137) {
			util.pressShiftX();
		}  else if (coreActionIndex == 138) {
			util.pressShiftC();
		}  else if (coreActionIndex == 139) {
			util.pressShiftV();
		}  else if (coreActionIndex == 140) {
			util.pressShiftB();
		}  else if (coreActionIndex == 141) {
			util.pressShiftN();
		}  else if (coreActionIndex == 142) {
			util.pressShiftM();
		}  else if (coreActionIndex == 143) {
			util.pressShiftComma();
		}  else if (coreActionIndex == 144) {
			util.pressShiftPeriod();
		}  else if (coreActionIndex == 145) {
			util.pressShiftSlash();
		}  else if (coreActionIndex == 146) {
			util.pressShiftUpArrow();
		}  else if (coreActionIndex == 147) {
			util.pressShiftDownArrow();
		}  else if (coreActionIndex == 148) {
			util.pressShiftLeftArrow();
		}  else if (coreActionIndex == 149) {
			util.pressShiftRightArrow();
		}  else if (coreActionIndex == 150) {
			util.pressShiftSpace();
		}  else if (coreActionIndex == 151) {
			util.pressControlShiftRightQuote();
		}  else if (coreActionIndex == 152) {
			util.pressControlShift1();
		}  else if (coreActionIndex == 153) {
			util.pressControlShift2();
		}  else if (coreActionIndex == 154) {
			util.pressControlShift3();
		}  else if (coreActionIndex == 155) {
			util.pressControlShift4();
		}  else if (coreActionIndex == 156) {
			util.pressControlShift5();
		}  else if (coreActionIndex == 157) {
			util.pressControlShift6();
		}  else if (coreActionIndex == 158) {
			util.pressControlShift7();
		}  else if (coreActionIndex == 159) {
			util.pressControlShift8();
		}  else if (coreActionIndex == 160) {
			util.pressControlShift9();
		}  else if (coreActionIndex == 161) {
			util.pressControlShift0();
		}  else if (coreActionIndex == 162) {
			util.pressControlShiftMinus();
		}  else if (coreActionIndex == 163) {
			util.pressControlShiftEqual();
		}  else if (coreActionIndex == 164) {
			util.pressControlShiftTab();
		}  else if (coreActionIndex == 165) {
			util.pressControlShiftQ();
		}  else if (coreActionIndex == 166) {
			util.pressControlShiftW();
		}  else if (coreActionIndex == 167) {
			util.pressControlShiftE();
		}  else if (coreActionIndex == 168) {
			util.pressControlShiftR();
		}  else if (coreActionIndex == 169) {
			util.pressControlShiftT();
		}  else if (coreActionIndex == 170) {
			util.pressControlShiftY();
		}  else if (coreActionIndex == 171) {
			util.pressControlShiftU();
		}  else if (coreActionIndex == 172) {
			util.pressControlShiftI();
		}  else if (coreActionIndex == 173) {
			util.pressControlShiftO();
		}  else if (coreActionIndex == 174) {
			util.pressControlShiftP();
		}  else if (coreActionIndex == 175) {
			util.pressControlShiftLeftBracket();
		}  else if (coreActionIndex == 176) {
			util.pressControlShiftRightBracket();
		}  else if (coreActionIndex == 177) {
			util.pressControlShiftBackslash();
		}  else if (coreActionIndex == 178) {
			util.pressControlShiftDelete();
		}  else if (coreActionIndex == 179) {
			util.pressControlShiftEnd();
		}  else if (coreActionIndex == 180) {
			util.pressControlShiftPageDown();
		}  else if (coreActionIndex == 181) {
			util.pressControlShiftA();
		}  else if (coreActionIndex == 182) {
			util.pressControlShiftS();
		}  else if (coreActionIndex == 183) {
			util.pressControlShiftD();
		}  else if (coreActionIndex == 184) {
			util.pressControlShiftF();
		}  else if (coreActionIndex == 185) {
			util.pressControlShiftG();
		}  else if (coreActionIndex == 186) {
			util.pressControlShiftH();
		}  else if (coreActionIndex == 187) {
			util.pressControlShiftJ();
		}  else if (coreActionIndex == 188) {
			util.pressControlShiftK();
		}  else if (coreActionIndex == 189) {
			util.pressControlShiftL();
		}  else if (coreActionIndex == 190) {
			util.pressControlShiftSemicolon();
		}  else if (coreActionIndex == 191) {
			util.pressControlShiftLeftQuote();
		}  else if (coreActionIndex == 192) {
			util.pressControlShiftEnter();
		}  else if (coreActionIndex == 193) {
			util.pressControlShiftZ();
		}  else if (coreActionIndex == 194) {
			util.pressControlShiftX();
		}  else if (coreActionIndex == 195) {
			util.pressControlShiftC();
		}  else if (coreActionIndex == 196) {
			util.pressControlShiftV();
		}  else if (coreActionIndex == 197) {
			util.pressControlShiftB();
		}  else if (coreActionIndex == 198) {
			util.pressControlShiftN();
		}  else if (coreActionIndex == 199) {
			util.pressControlShiftM();
		}  else if (coreActionIndex == 200) {
			util.pressControlShiftComma();
		}  else if (coreActionIndex == 201) {
			util.pressControlShiftPeriod();
		}  else if (coreActionIndex == 202) {
			util.pressControlShiftSlash();
		}  else if (coreActionIndex == 203) {
			util.pressControlShiftUpArrow();
		}  else if (coreActionIndex == 204) {
			util.pressControlShiftDownArrow();
		}  else if (coreActionIndex == 205) {
			util.pressControlShiftRightArrow();
		}  else if (coreActionIndex == 206) {
			util.pressControlShiftLeftArrow();
		}  else if (coreActionIndex == 207) {
			util.pressControlShiftSpace();
		}  else if (coreActionIndex == 208) {
			util.pressControlRightQuote();
		}  else if (coreActionIndex == 209) {
			util.pressControl1();
		}  else if (coreActionIndex == 210) {
			util.pressControl2();
		}  else if (coreActionIndex == 211) {
			util.pressControl3();
		}  else if (coreActionIndex == 212) {
			util.pressControl4();
		}  else if (coreActionIndex == 213) {
			util.pressControl5();
		}  else if (coreActionIndex == 214) {
			util.pressControl6();
		}  else if (coreActionIndex == 215) {
			util.pressControl7();
		}  else if (coreActionIndex == 216) {
			util.pressControl8();
		}  else if (coreActionIndex == 217) {
			util.pressControl9();
		}  else if (coreActionIndex == 218) {
			util.pressControl0();
		}  else if (coreActionIndex == 219) {
			util.pressControlMinus();
		}  else if (coreActionIndex == 220) {
			util.pressControlEqual();
		}  else if (coreActionIndex == 221) {
			util.pressControlTab();
		}  else if (coreActionIndex == 222) {
			util.pressControlQ();
		}  else if (coreActionIndex == 223) {
			util.pressControlW();
		}  else if (coreActionIndex == 224) {
			util.pressControlE();
		}   else if (coreActionIndex == 225) {
			util.pressControlR();
		}   else if (coreActionIndex == 226) {
			util.pressControlT();
		}   else if (coreActionIndex == 227) {
			util.pressControlY();
		}   else if (coreActionIndex == 228) {
			util.pressControlU();
		}   else if (coreActionIndex == 229) {
			util.pressControlI();
		}   else if (coreActionIndex == 230) {
			util.pressControlO();
		}   else if (coreActionIndex == 231) {
			util.pressControlP();
		}   else if (coreActionIndex == 232) {
			util.pressControlLeftBracket();
		}   else if (coreActionIndex == 233) {
			util.pressControlRightBracket();
		}   else if (coreActionIndex == 234) {
			util.pressControlBackslash();
		}   else if (coreActionIndex == 235) {
			util.pressControlDelete();
		}   else if (coreActionIndex == 236) {
			util.pressControlEnd();
		}   else if (coreActionIndex == 237) {
			util.pressControlPageDown();
		}   else if (coreActionIndex == 238) {
			util.pressControlA();
		}   else if (coreActionIndex == 239) {
			util.pressControlS();
		}   else if (coreActionIndex == 240) {
			util.pressControlD();
		}   else if (coreActionIndex == 241) {
			util.pressControlF();
		}   else if (coreActionIndex == 242) {
			util.pressControlG();
		}   else if (coreActionIndex == 243) {
			util.pressControlH();
		}   else if (coreActionIndex == 244) {
			util.pressControlJ();
		}   else if (coreActionIndex == 245) {
			util.pressControlK();
		}   else if (coreActionIndex == 246) {
			util.pressControlL();
		}   else if (coreActionIndex == 247) {
			util.pressControlSemicolon();
		}   else if (coreActionIndex == 248) {
			util.pressControlLeftQuote();
		}   else if (coreActionIndex == 249) {
			util.pressControlEnter();
		}   else if (coreActionIndex == 250) {
			util.pressControlZ();
		}   else if (coreActionIndex == 251) {
			util.pressControlX();
		}   else if (coreActionIndex == 252) {
			util.pressControlC();
		}   else if (coreActionIndex == 253) {
			util.pressControlV();
		}   else if (coreActionIndex == 254) {
			util.pressControlB();
		}   else if (coreActionIndex == 255) {
			util.pressControlN();
		}   else if (coreActionIndex == 256) {
			util.pressControlM();
		}   else if (coreActionIndex == 257) {
			util.pressControlComma();
		}    else if (coreActionIndex == 258) {
			util.pressControlPeriod();
		}    else if (coreActionIndex == 259) {
			util.pressControlSlash();
		}    else if (coreActionIndex == 260) {
			util.pressControlUpArrow();
		}    else if (coreActionIndex == 261) {
			util.pressControlDownArrow();
		}    else if (coreActionIndex == 262) {
			util.pressControlLeftArrow();
		}    else if (coreActionIndex == 263) {
			util.pressControlSpace();
		}    else if (coreActionIndex == 264) {
			util.pressAltRightQuote();
		}    else if (coreActionIndex == 265) {
			util.pressAlt1();
		}    else if (coreActionIndex == 266) {
			util.pressAlt2();
		}    else if (coreActionIndex == 267) {
			util.pressAlt3();
		}    else if (coreActionIndex == 268) {
			util.pressAlt4();
		}    else if (coreActionIndex == 269) {
			util.pressAlt5();
		}    else if (coreActionIndex == 270) {
			util.pressAlt6();
		}    else if (coreActionIndex == 271) {
			util.pressAlt7();
		}    else if (coreActionIndex == 272) {
			util.pressAlt8();
		}    else if (coreActionIndex == 273) {
			util.pressAlt9();
		}    else if (coreActionIndex == 274) {
			util.pressAlt0();
		}    else if (coreActionIndex == 275) {
			util.pressAltMinus();
		}    else if (coreActionIndex == 276) {
			util.pressAltEqual();
		}    else if (coreActionIndex == 277) {
			util.pressAltTab();
		}    else if (coreActionIndex == 278) {
			util.pressAltQ();
		}    else if (coreActionIndex == 279) {
			util.pressAltW();
		}    else if (coreActionIndex == 280) {
			util.pressAltE();
		}    else if (coreActionIndex == 281) {
			util.pressAltR();
		}    else if (coreActionIndex == 282) {
			util.pressAltT();
		}    else if (coreActionIndex == 283) {
			util.pressAltY();
		}    else if (coreActionIndex == 284) {
			util.pressAltU();
		}    else if (coreActionIndex == 285) {
			util.pressAltI();
		}    else if (coreActionIndex == 286) {
			util.pressAltO();
		}    else if (coreActionIndex == 287) {
			util.pressAltP();
		} else if (coreActionIndex == 288) {
			util.pressAltLeftBracket();
		} else if (coreActionIndex == 289) {
			util.pressAltRightBracket();
		} else if (coreActionIndex == 290) {
			util.pressAltBackslash();
		} else if (coreActionIndex == 291) {
			util.pressAltDelete();
		} else if (coreActionIndex == 292) {
			util.pressAltEnd();
		} else if (coreActionIndex == 293) {
			util.pressAltPageDown();
		} else if (coreActionIndex == 294) {
			util.pressAltA();
		} else if (coreActionIndex == 295) {
			util.pressAltS();
		} else if (coreActionIndex == 296) {
			util.pressAltD();
		} else if (coreActionIndex == 297) {
			util.pressAltF();
		} else if (coreActionIndex == 298) {
			util.pressAltG();
		} else if (coreActionIndex == 299) {
			util.pressAltH();
		} else if (coreActionIndex == 300) {
			util.pressAltJ();
		} else if (coreActionIndex == 301) {
			util.pressAltK();
		} else if (coreActionIndex == 302) {
			util.pressAltL();
		} else if (coreActionIndex == 303) {
			util.pressAltSemicolon();
		} else if (coreActionIndex == 304) {
			util.pressAltLeftQuote();
		} else if (coreActionIndex == 305) {
			util.pressAltEnter();
		} else if (coreActionIndex == 306) {
			util.pressAltZ();
		} else if (coreActionIndex == 307) {
			util.pressAltX();
		} else if (coreActionIndex == 308) {
			util.pressAltC();
		} else if (coreActionIndex == 309) {
			util.pressAltV();
		} else if (coreActionIndex == 310) {
			util.pressAltB();
		} else if (coreActionIndex == 311) {
			util.pressAltN();
		} else if (coreActionIndex == 312) {
			util.pressAltM();
		} else if (coreActionIndex == 313) {
			util.pressAltComma();
		} else if (coreActionIndex == 314) {
			util.pressAltPeriod();
		} else if (coreActionIndex == 315) {
			util.pressAltSlash();
		} else if (coreActionIndex == 316) {
			util.pressAltUpArrow();
		} else if (coreActionIndex == 317) {
			util.pressAltDownArrow();
		} else if (coreActionIndex == 318) {
			util.pressAltLeftArrow();
		} else if (coreActionIndex == 319) {
			util.pressAltRightArrow();
		} else if (coreActionIndex == 320) {
			util.pressAltSpace();
		}    else if (coreActionIndex == 321) {
			util.pressControlAltRightQuote();
		}    else if (coreActionIndex == 322) {
			util.pressControlAlt1();
		}    else if (coreActionIndex == 323) {
			util.pressControlAlt2();
		}    else if (coreActionIndex == 324) {
			util.pressControlAlt3();
		}    else if (coreActionIndex == 325) {
			util.pressControlAlt4();
		}    else if (coreActionIndex == 326) {
			util.pressControlAlt5();
		}    else if (coreActionIndex == 327) {
			util.pressControlAlt6();
		}    else if (coreActionIndex == 328) {
			util.pressControlAlt7();
		}    else if (coreActionIndex == 329) {
			util.pressControlAlt8();
		}    else if (coreActionIndex == 330) {
			util.pressControlAlt9();
		}    else if (coreActionIndex == 331) {
			util.pressControlAlt0();
		}    else if (coreActionIndex == 332) {
			util.pressControlAltMinus();
		}    else if (coreActionIndex == 333) {
			util.pressControlAltEqual();
		}    else if (coreActionIndex == 334) {
			util.pressControlAltTab();
		}    else if (coreActionIndex == 335) {
			util.pressControlAltQ();
		}    else if (coreActionIndex == 336) {
			util.pressControlAltW();
		}    else if (coreActionIndex == 337) {
			util.pressControlAltE();
		}    else if (coreActionIndex == 338) {
			util.pressControlAltR();
		}    else if (coreActionIndex == 339) {
			util.pressControlAltT();
		}    else if (coreActionIndex == 340) {
			util.pressControlAltY();
		}    else if (coreActionIndex == 341) {
			util.pressControlAltU();
		}    else if (coreActionIndex == 342) {
			util.pressControlAltI();
		}    else if (coreActionIndex == 343) {
			util.pressControlAltO();
		}    else if (coreActionIndex == 344) {
			util.pressControlAltP();
		} else if (coreActionIndex == 345) {
			util.pressControlAltLeftBracket();
		} else if (coreActionIndex == 346) {
			util.pressControlAltRightBracket();
		} else if (coreActionIndex == 347) {
			util.pressControlAltBackslash();
		} else if (coreActionIndex == 348) {
			util.pressControlAltDelete();
		} else if (coreActionIndex == 349) {
			util.pressControlAltEnd();
		} else if (coreActionIndex == 350) {
			util.pressControlAltPageDown();
		} else if (coreActionIndex == 351) {
			util.pressControlAltA();
		} else if (coreActionIndex == 352) {
			util.pressControlAltS();
		} else if (coreActionIndex == 353) {
			util.pressControlAltD();
		} else if (coreActionIndex == 354) {
			util.pressControlAltF();
		} else if (coreActionIndex == 355) {
			util.pressControlAltG();
		} else if (coreActionIndex == 356) {
			util.pressControlAltH();
		} else if (coreActionIndex == 357) {
			util.pressControlAltJ();
		} else if (coreActionIndex == 358) {
			util.pressControlAltK();
		} else if (coreActionIndex == 359) {
			util.pressControlAltL();
		} else if (coreActionIndex == 360) {
			util.pressControlAltSemicolon();
		} else if (coreActionIndex == 361) {
			util.pressControlAltLeftQuote();
		} else if (coreActionIndex == 362) {
			util.pressControlAltEnter();
		} else if (coreActionIndex == 363) {
			util.pressControlAltZ();
		} else if (coreActionIndex == 364) {
			util.pressControlAltX();
		} else if (coreActionIndex == 365) {
			util.pressControlAltC();
		} else if (coreActionIndex == 366) {
			util.pressControlAltV();
		} else if (coreActionIndex == 367) {
			util.pressControlAltB();
		} else if (coreActionIndex == 368) {
			util.pressControlAltN();
		} else if (coreActionIndex == 369) {
			util.pressControlAltM();
		} else if (coreActionIndex == 370) {
			util.pressControlAltComma();
		} else if (coreActionIndex == 371) {
			util.pressControlAltPeriod();
		} else if (coreActionIndex == 372) {
			util.pressControlAltSlash();
		} else if (coreActionIndex == 373) {
			util.pressControlAltUpArrow();
		} else if (coreActionIndex == 374) {
			util.pressControlAltDownArrow();
		} else if (coreActionIndex == 375) {
			util.pressControlAltLeftArrow();
		} else if (coreActionIndex == 376) {
			util.pressControlAltRightArrow();
		} else if (coreActionIndex == 377) {
			util.pressControlAltSpace();
		} else if (coreActionIndex == 378) {
			util.pressControlEscape();
		} else if (coreActionIndex == 379) {
			util.pressControlF1();
		} else if (coreActionIndex == 380) {
			util.pressControlF2();
		} else if (coreActionIndex == 381) {
			util.pressControlF3();
		} else if (coreActionIndex == 382) {
			util.pressControlF4();
		} else if (coreActionIndex == 383) {
			util.pressControlF5();
		} else if (coreActionIndex == 384) {
			util.pressControlF6();
		} else if (coreActionIndex == 385) {
			util.pressControlF7();
		} else if (coreActionIndex == 386) {
			util.pressControlF8();
		} else if (coreActionIndex == 387) {
			util.pressControlF9();
		} else if (coreActionIndex == 388) {
			util.pressControlF10();
		} else if (coreActionIndex == 389) {
			util.pressControlF11();
		} else if (coreActionIndex == 390) {
			util.pressControlF12();
		} else if (coreActionIndex == 391) {
			util.pressShiftEscape();
		} else if (coreActionIndex == 392) {
			util.pressShiftF1();
		} else if (coreActionIndex == 393) {
			util.pressShiftF2();
		} else if (coreActionIndex == 394) {
			util.pressShiftF3();
		} else if (coreActionIndex == 395) {
			util.pressShiftF4();
		} else if (coreActionIndex == 396) {
			util.pressShiftF5();
		} else if (coreActionIndex == 397) {
			util.pressShiftF6();
		} else if (coreActionIndex == 398) {
			util.pressShiftF7();
		} else if (coreActionIndex == 399) {
			util.pressShiftF8();
		} else if (coreActionIndex == 400) {
			util.pressShiftF9();
		} else if (coreActionIndex == 401) {
			util.pressShiftF10();
		} else if (coreActionIndex == 402) {
			util.pressShiftF11();
		} else if (coreActionIndex == 403) {
			util.pressShiftF12();
		} else if (coreActionIndex == 404) {
			util.pressAltF1();
		} else if (coreActionIndex == 405) {
			util.pressAltF2();
		} else if (coreActionIndex == 406) {
			util.pressAltF3();
		} else if (coreActionIndex == 407) {
			util.pressAltF4();
		} else if (coreActionIndex == 408) {
			util.pressAltF5();
		} else if (coreActionIndex == 409) {
			util.pressAltF6();
		} else if (coreActionIndex == 410) {
			util.pressAltF7();
		} else if (coreActionIndex == 411) {
			util.pressAltF8();
		} else if (coreActionIndex == 412) {
			util.pressAltF9();
		} else if (coreActionIndex == 413) {
			util.pressAltF10();
		} else if (coreActionIndex == 414) {
			util.pressAltF11();
		} else if (coreActionIndex == 415) {
			util.pressAltF12();
		} else if (coreActionIndex == 416) {
			util.pressControlShiftEscape();
		} else if (coreActionIndex == 417) {
			util.pressControlShiftF1();
		} else if (coreActionIndex == 418) {
			util.pressControlShiftF2();
		} else if (coreActionIndex == 419) {
			util.pressControlShiftF3();
		} else if (coreActionIndex == 420) {
			util.pressControlShiftF4();
		} else if (coreActionIndex == 421) {
			util.pressControlShiftF5();
		} else if (coreActionIndex == 422) {
			util.pressControlShiftF6();
		} else if (coreActionIndex == 423) {
			util.pressControlShiftF7();
		} else if (coreActionIndex == 424) {
			util.pressControlShiftF8();
		} else if (coreActionIndex == 425) {
			util.pressControlShiftF9();
		} else if (coreActionIndex == 426) {
			util.pressControlShiftF10();
		} else if (coreActionIndex == 427) {
			util.pressControlShiftF11();
		} else if (coreActionIndex == 428) {
			util.pressControlShiftF12();
		} else if (coreActionIndex == 429) {
			util.pressControlAltEscape();
		}
	}
}
