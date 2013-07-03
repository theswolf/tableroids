package com.september.tableroids.statics;

import java.util.Random;

import android.util.SparseArray;

public class Scorer {

	private static Integer moltiplicando;
	private static Integer moltiplicatore;
	private static int responseCounter=0;
	private static Random r;
	private static SparseArray<Response> responses;
	
	public static class Response {
		private  Integer moltiplicando;
		private  Integer moltiplicatore;
		private  Integer response;
		private Integer responseNumber;
		
		
		public Integer getResponseNumber() {
			return responseNumber;
		}
		public void setResponseNumber(Integer responseNumber) {
			this.responseNumber = responseNumber;
		}
		public  Integer getMoltiplicando() {
			return moltiplicando;
		}
		public  void setMoltiplicando(Integer moltiplicando) {
			this.moltiplicando = moltiplicando;
		}
		public  Integer getMoltiplicatore() {
			return moltiplicatore;
		}
		public  void setMoltiplicatore(Integer moltiplicatore) {
			this.moltiplicatore = moltiplicatore;
		}
		public  Integer getResponse() {
			return response;
		}
		public  void setResponse(Integer response) {
			this.response = response;
		}
		
		
	}

	public static Integer getMoltiplicando() {
		if(moltiplicando == null) {
			setMoltiplicando(getR().nextInt(9)+1);
		}
		return moltiplicando;
	}

	public static void setMoltiplicando(Integer moltiplicando) {
		Scorer.moltiplicando = moltiplicando;
	}

	public static Integer getMoltiplicatore() {
		if(moltiplicatore == null) {
			setMoltiplicatore(getR().nextInt(9)+1);
		}
		return moltiplicatore;
	}

	public static void setMoltiplicatore(Integer moltiplicatore) {
		Scorer.moltiplicatore = moltiplicatore;
	}

	public static int getResponseCounter() {
		return responseCounter;
	}

	public static void increaseResponseCounter() {
		responseCounter++;
	}

	public static Random getR() {
		if(r==null) {
			setR(new Random());
		}
		return r;
	}

	public static void setR(Random r) {
		Scorer.r = r;
	}

	public static SparseArray<Response> getResponses() {
		if(responses == null) {
			setResponses(new SparseArray<Scorer.Response>());
		}
		return responses;
	}

	private static void setResponses(SparseArray<Response> responses) {
		Scorer.responses = responses;
	}
	
	public static void putResponse(Integer value) {
		increaseResponseCounter();
		Response response = new Response();
		response.setResponseNumber(getResponseCounter());
		response.setResponse(value);
		response.setMoltiplicando(getMoltiplicando());
		response.getMoltiplicatore();
		getResponses().append(getResponseCounter(), response);
		setMoltiplicando(null);
		setMoltiplicatore(null);
	}
	
	
}
