package network;

public class RelogioLamport {
	private float relogio;
	public RelogioLamport(float id) {
		relogio = id/10;
	}
	public synchronized void eventoLocal() {
		relogio++;
	}
	public synchronized void eventoMsg(float relogioMsg) {
		relogio = Math.max(relogio, relogioMsg++);
		this.eventoLocal();
	}
	public float getRelogio() {
		return this.relogio;
	}
}
