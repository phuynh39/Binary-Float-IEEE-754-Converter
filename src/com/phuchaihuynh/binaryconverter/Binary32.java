package com.phuchaihuynh.binaryconverter;

import java.util.Hashtable;

public class Binary32 {	
	private static final String ZERO = "0000";
	private static final String ONE = "0001";
	private static final String TWO = "0010";
	private static final String THREE = "0011";
	private static final String FOUR = "0100";
	private static final String FIVE = "0101";
	private static final String SIX = "0110";
	private static final String SEVEN = "0111";
	private static final String EIGHT = "1000";
	private static final String NINE = "1001";
	private static final String TEN = "1010";
	private static final String ELEVEN = "1011";
	private static final String TWELVE = "1100";
	private static final String THIRDTEEN = "1101";
	private static final String FOURTEEN = "1110";
	private static final String FIFTEEN = "1111";
	private static Hashtable<String, String> table = new Hashtable<String, String>();

	private String number;
	private String binary;
	private String hex;
	private String signBit;
	private String exponentBits;
	private String mantissaBits;

	public Binary32(String number, String type) {
		if (type.equals(MainActivity.NUMBER)) {
			float n = Float.parseFloat(number);
			if (n <= Float.MAX_VALUE && n >= (float)-1*Float.MAX_VALUE) {
				this.number = number;
				this.binary = toBinary32(convertToBinary(number));
				this.hex = toHex(this.signBit+this.exponentBits+this.mantissaBits);
			}
			else {
				this.number = number;
				this.binary = "null";
				this.hex = "null";
			}
		}
		else if (type.equals(MainActivity.BINARY)) {
			boolean isBin = true;
			for (int i = 0; i < number.length(); i++) {
				if (number.charAt(i) != '0' && number.charAt(i) != '1') {
					isBin = false;
				}
			}
			if (isBin && number.length() == 32) {
				this.binary = toBinary32(number);
				this.number = convertToFloat(number);
				this.hex = toHex(number);
			}
			else {
				this.binary = number;
				this.number = "null";
				this.hex = "null";
			}

		}
		else if (type.equals(MainActivity.HEX)) {
			boolean isHex = true;
			for (int i = 0; i < number.length(); i++) {
				if (number.charAt(i) != '0' && number.charAt(i) != '1' && number.charAt(i) != '2' && number.charAt(i) != '3' &&
						number.charAt(i) != '4' && number.charAt(i) != '5' && number.charAt(i) != '6' && number.charAt(i) != '7' &&
						number.charAt(i) != '8' && number.charAt(i) != '9' && number.charAt(i) != 'a' && number.charAt(i) != 'b' &&
						number.charAt(i) != 'c' && number.charAt(i) != 'd' && number.charAt(i) != 'e' && number.charAt(i) != 'f') {
					isHex = false;
				}
			}
			if (isHex && number.length() == 8) {
				this.hex = number;
				String b  = hexToBinary(number);
				this.binary = toBinary32(b);
				this.number = convertToFloat(b);
			}
			else {
				this.hex = number;
				this.binary = "null";
				this.number = "null";
			}
		}
	}

	public String getSignBit() {
		return this.signBit;
	}

	public String getExponentBits() {
		return this.exponentBits;
	}

	public String getMantissaBits() {
		return this.mantissaBits;
	}

	public String getNumber() {
		return this.number;
	}

	public String getBinary() {
		return this.binary;
	}

	public String getHex() {
		return this.hex;
	}

	private String hexToBinary(String hex) {
		table.put("0", "0000");
		table.put("1", "0001");
		table.put("2", "0010");
		table.put("3", "0011");
		table.put("4", "0100");
		table.put("5", "0101");
		table.put("6", "0110");
		table.put("7", "0111");
		table.put("8", "1000");
		table.put("9", "1001");
		table.put("a", "1010");
		table.put("b", "1011");
		table.put("c", "1100");
		table.put("d", "1101");
		table.put("e", "1110");
		table.put("f", "1111");
		StringBuilder sb = new StringBuilder(hex);
		StringBuilder binary = new StringBuilder();
		for (int i = 0; i < sb.length(); i++) {
			binary.append(table.get(String.valueOf(sb.charAt(i))));
		}
		return binary.toString();
	}

	private String convertToFloat(String binary) {
		StringBuilder sb = new StringBuilder();
		this.signBit = String.valueOf(binary.charAt(0));
		//Get the exponent
		String exponent = binary.substring(1,9);
		this.exponentBits = exponent;
		int exp = Integer.parseInt(exponent,2) - 127;

		//Explore the Mantissa
		String mantissa1 = binary.substring(9);
		this.mantissaBits = mantissa1;
		String part2="";
		String part1="";
		if (binary.charAt(0) == '1') {
			sb = sb.append("-");
		}
		if ((exp>=0) && (exp!=128))  {
			String mantissa = "1" + mantissa1;
			if (exp < 24) {
				part1 = mantissa.substring(0, exp+1);
				int beforedot = Integer.parseInt(part1, 2);
				sb = sb.append(beforedot);
				sb = sb.append(".");
				part2 = mantissa.substring(exp+1);
				double a = 0;
				for (int i = 0; i < part2.length(); i++) {
					double j = i;
					if (part2.charAt(i) == '1') {
						a = a + Math.pow(2, (j*(-1)-1));
					}
				}
				part2 = String.valueOf(a);
				sb = sb.append(part2.substring(2));
			}
			else if (exp >= 24) {
				double c = exp-24;
				StringBuilder sb3 = new StringBuilder();
				for (int i = 0; i <= c ; i++) {
					sb3 = sb3.append(0);
				}
				part1 = "1" + mantissa1 + sb3.toString();
				double d = 0.0;
				for (int i = 0; i < part1.length(); i++) {
					if (part1.charAt(i) == '1') {
						d = d + Math.pow(2, part1.length() - i - 1);
					}
				}
				sb = sb.append(String.valueOf(d));
			}
		}
		else if ((exp < 0) && (exp != -127)) {
			exp = exp * (-1);
			StringBuilder sb1 = new StringBuilder();
			for (int i = 1; i < exp; i++) {
				sb1 = sb1.append("0");
			}
			String s2 = sb1.toString() + "1" + mantissa1;
			double a = 0;
			for (int i = 0; i < s2.length(); i++) {
				double j = i;
				if (s2.charAt(i) == '1') {
					a = a + Math.pow(2, (j*(-1)-1));
				}
			}
			s2 = String.valueOf(a);
			sb = sb.append(s2);
		}

		//Special case 1
		else if (exp == -127) {
			StringBuilder sb1 = new StringBuilder();
			for (int i = 1; i < 126; i++) {
				sb1 = sb1.append("0");
			}
			String s3 = sb1 + "0" + mantissa1;
			double a = 0;
			for (int i = 0; i < s3.length(); i++) {
				double j = i;
				if (s3.charAt(i) == '1') {
					a = a + Math.pow(2, (j*(-1)-1));
				}
			}
			s3 = String.valueOf(a);
			sb = sb.append(s3);
		}

		//Special case 2, 3
		else if (exp == 128) {
			if (Integer.parseInt(mantissa1, 2)==0) { 
				sb = sb.append("inf");
			}
			else {
				if (binary.charAt(0) == '1') {
					sb = sb.deleteCharAt(0);
				}
				sb = sb.append("NaN");
			}
		}
		return sb.toString();
	}



	private String convertToBinary(String number) {	
		String hexString = Float.toHexString((Float.parseFloat(number)));
		//Compute the sign bit
		if (hexString.charAt(0) == '-') {
			this.signBit = "1";
		}
		else {
			this.signBit = "0";
		}
		if (hexString.equals("0x0.0p0") || hexString.equals("-0x0.0p0")) {
			this.exponentBits = "00000000";
			this.mantissaBits = "00000000000000000000000";
		}
		else {
			// Compute the mantissa bits and exponent bits
			String[] splits = hexString.split("\\.");
			String hexMantissa =splits[1].split("p")[0];
			int power = Integer.parseInt(splits[1].split("p")[1]) + 127;
			//Compute the mantissa
			StringBuilder mantissa = new StringBuilder();
			int b = 0;
			for (int i = 0; i < hexMantissa.length(); i++) {
				if (hexMantissa.charAt(i) == 'a') {
					b = 10;
				}
				else if (hexMantissa.charAt(i) == 'b') {
					b = 11;
				}
				else if (hexMantissa.charAt(i) == 'c') {
					b = 12;
				}
				else if (hexMantissa.charAt(i) == 'd') {
					b = 13;
				}
				else if (hexMantissa.charAt(i) == 'e') {
					b = 14;
				}
				else if (hexMantissa.charAt(i) == 'f') {
					b = 15;
				}
				else {
					b = Integer.parseInt(String.valueOf(hexMantissa.charAt(i)));
				}
				StringBuilder bits = new StringBuilder(Integer.toBinaryString(b));
				int diff = 4 - bits.length();
				if (diff == 0) {
					mantissa.append(bits);
				}
				else if (diff == 1) {
					mantissa.append("0").append(bits);
				}
				else if (diff == 2) {
					mantissa.append("00").append(bits);
				}
				else if (diff == 3 ) {
					mantissa.append("000").append(bits);
				}	
			}
			int trailingLen = 23 - mantissa.length();
			for (int i = 0; i < trailingLen; i++) {
				mantissa.append("0");
			}
			if (mantissa.length() > 23) {
				mantissa = mantissa.deleteCharAt(23);
			}
			this.mantissaBits = mantissa.toString();
			//Compute exponent bits
			StringBuilder exponent = new StringBuilder(Integer.toBinaryString(power));
			int diff = 8 - exponent.length();
			for (int i = 0; i < diff; i++) {
				exponent.insert(0, "0");
			}
			this.exponentBits = exponent.toString(); 
		}
		return (this.signBit + this.exponentBits + this.mantissaBits);
	}

	private String toBinary32(String binary) {
		StringBuilder newString = new StringBuilder(binary);
		newString.insert(8, ' ').insert(17, ' ').insert(26, ' ');
		return newString.toString();
	}

	private String toHex(String binary) {
		table.put(ZERO, "0");
		table.put(ONE, "1");
		table.put(TWO, "2");
		table.put(THREE, "3");
		table.put(FOUR, "4");
		table.put(FIVE, "5");
		table.put(SIX, "6");
		table.put(SEVEN, "7");
		table.put(EIGHT, "8");
		table.put(NINE, "9");
		table.put(TEN, "a");
		table.put(ELEVEN, "b");
		table.put(TWELVE, "c");
		table.put(THIRDTEEN, "d");
		table.put(FOURTEEN, "e");
		table.put(FIFTEEN, "f");
		StringBuilder sb = new StringBuilder(binary);
		StringBuilder hex = new StringBuilder();
		for (int i = 0; i < 32; i+=4) {
			hex.append(table.get(sb.substring(i, i+4)));
		}
		return hex.toString();
	}
}