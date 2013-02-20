package com.phuchaihuynh.binaryconverter;

import java.util.Hashtable;

public class OneComplementBinary {
	public final static Long MAX_UNSIGNED_INTEGER = (long) 2*Integer.MAX_VALUE + 1;
	public final static int MIN_UNSIGNED_INTEGER = 0;

	private static Hashtable<String, String> table = new Hashtable<String, String>();

	private String number;
	private String binary;
	private String hex;

	public OneComplementBinary(String number, String type) {
		if (type.equals(MainActivity.NUMBER)) {
			Long n = Long.parseLong(number);
			if (n <= MAX_UNSIGNED_INTEGER && n >= MIN_UNSIGNED_INTEGER) {
				this.number = number;
				this.binary = toOneComplementBinary(number);
				this.hex = toHex(number);
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
				if (number.charAt(i) != '0' && number.charAt(i) != '1' && number.charAt(i) != ' ') {
					isBin = false;
				}
			}
			if (isBin) {
				this.binary = fillTo32Bits(number);
				this.number = binaryToUnsignedInteger(number);
				this.hex = toHex(this.number);
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
						number.charAt(i) != 'c' && number.charAt(i) != 'd' && number.charAt(i) != 'e' && number.charAt(i) != 'f' && 
						number.charAt(i) != ' ') {
					isHex = false;
				}
			}
			if (isHex) {
				this.hex = number;
				this.binary = fillTo32Bits(hexToBinary(number));
				this.number = binaryToUnsignedInteger(hexToBinary(number));
			}
			else {
				this.binary = "null";
				this.number = "null";
				this.hex = number;
			}
		}
	}

	public String getNumber() {
		return this.number;
	}

	public String getOneComplementBinary() {
		return this.binary;
	}

	public String getHex() {
		return this.hex;
	}

	private String fillTo32Bits(String binary) {
		int diff = 32 - binary.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < diff; i++) {
			sb.append(0);
		}
		sb = sb.append(binary);
		sb.insert(8, ' ').insert(17, ' ').insert(26, ' ');
		return sb.toString();
	}

	private String binaryToUnsignedInteger(String binary) {
		StringBuilder sb = new StringBuilder(binary);
		long sum = 0;
		for (int i = 0; i < binary.length(); i++) {
			if (sb.charAt(i) == '1') {
				sum += Math.pow(2, binary.length()-1-i);
			}
		}
		return String.valueOf(sum);
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

	private String toOneComplementBinary(String number) {
		long n = Long.parseLong(number);
		StringBuilder b = new StringBuilder(32);
		if (n > 0) { 
			while (n != 0) {
				long r = n % 2;
				b.append(r);
				n = n/2;
			}
			b = b.reverse();
			if (b.length() < 32) {
				int temp = 32 - b.length();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < temp; i++) {
					sb.append(0);
				}
				b = sb.append(b);
			}
		}
		b.insert(8, ' ').insert(17, ' ').insert(26, ' ');
		return b.toString();
	}

	private String toHex(String number) {
		long n = Long.parseLong(number);
		return Long.toHexString(n);
	}
}
