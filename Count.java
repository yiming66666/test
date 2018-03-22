package counts;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Count extends JFrame {
	public Count() {
		this.setTitle("数学表达式计算器");
		final JTextField jt = new JTextField();
		final JButton jb = new JButton("计算");
		final JLabel jl = new JLabel("0.00");
		jt.setFont(new Font("宋体", Font.PLAIN, 48));
		jl.setFont(new Font("宋体", Font.PLAIN, 48));

		jt.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						jl.setFont(new Font("宋体", Font.PLAIN, 48));

						jl.setText(Count.this.calculate(jt.getText().trim()));
					} catch (Exception gj) {
						jl.setFont(new Font("宋体", Font.PLAIN, 18));
						jl.setText("未知错误，请检查表达式");
					}
				}

				else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_A) {
					jt.setText("");

				} else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_D) {
					jt.setText(jl.getText());

				}
			}
		});
		this.setBounds(500, 200, 400, 260);

		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.add(Box.createVerticalStrut(10));
		this.add(jt);
		jt.setSize(400, 20);
		this.add(Box.createVerticalStrut(10));

		this.add(jb);
		jb.setSize(100, 50);
		this.add(Box.createVerticalStrut(10));

		jb.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == jb) {
					try {
						jl.setFont(new Font("宋体", Font.PLAIN, 48));

						jl.setText(Count.this.calculate(jt.getText().trim()));
					} catch (Exception gj) {
						jl.setFont(new Font("宋体", Font.PLAIN, 18));
						jl.setText("未知错误，请检查表达式");

					}
				}
			}
		});
		this.add(jl);
		jl.setOpaque(true);
		jl.setBackground(Color.white);
		jl.setSize(400, 30);
		JLabel jl1 = new JLabel("版权所有  @云");
		this.add(Box.createVerticalStrut(10));

		this.add(jl1);
		jl1.setSize(210, 20);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public String calculate(String da) {
		for (int i = 0; i < da.length(); i++) {
			char j = da.charAt(i);

			if (j == ')') {

				for (int l = i; l >= 0; l--) {
					if (da.charAt(l) == '(') {
						String tem1 = da.substring(0, l);
						String temp2 = da.substring(i + 1);
						da = tem1 + this.tempStringC(da.substring(l + 1, i))
								+ temp2;
						return calculate(da);
					}

				}
			}

		}

		return tempStringC(da);

	}

	public String tempStringC(String da) {
		da = "?" + da + "?";
		da = this.cutdiv(da);
		for (int i = 0; i < da.length(); i++) {

			char j = da.charAt(i);

			if (j == '/' || j == '*') {
				String tem1 = "";
				String temp2 = "";
				BigDecimal d1 = null;
				BigDecimal d2 = null;

				for (int k = i; k >= 0; k--) {
					if ("0123456789E.".indexOf(da.charAt(k - 1) + "") == -1) {

						tem1 = da.substring(0, k);
						if (tem1.equals("?")) {
							tem1 = "";
						}
						d1 = new BigDecimal(da.substring(k, i));
						break;
					}
				}

				out: for (int k = i; k < da.length(); k++) {

					if ("012345678E9.".indexOf(da.charAt(k + 1) + "") == -1) {
						if (da.charAt(i + 1) == '+' || da.charAt(i + 1) == '-') {

							for (int lp = i + 1; lp < da.length(); lp++) {

								if ("012345678E9.".indexOf(da.charAt(lp + 1)
										+ "") == -1) {
									System.out.println(da);
									System.out.println(lp);
									d2 = new BigDecimal(da.substring(i + 1,
											lp + 1));
									temp2 = da.substring(lp + 1);
									break out;
								}
							}
						}
						temp2 = da.substring(k + 1);
						if (temp2.equals("?")) {
							temp2 = "";
						}

						d2 = new BigDecimal(da.substring(i + 1, k + 1));
						break;
					}

				}
				if (j == '/') {
					da = tem1
							+ d1.divide(d2, 10, BigDecimal.ROUND_HALF_UP)
									.doubleValue() + temp2;
				} else if (j == '*') {
					da = tem1 + d1.multiply(d2).doubleValue() + temp2;

				}

				return tempStringC(this.trimes(da));
			}

		}
		return tempSum(da.substring(1, da.length() - 1));

	}

	public String tempSum(String da) {
		da = "?" + da + "?";
		int z = 0;
		da = this.cutMerge(da);
		oout: for (int i = 0; i < da.length(); i++) {

			char j = da.charAt(i);

			if (j == '+' || j == '-') {
				String tem1 = "";
				String temp2 = "";
				BigDecimal d1 = null;
				BigDecimal d2 = null;
				z = i;

				out: for (int k = i; k >= 0; k--) {
					if ("0123456789E.".indexOf(da.charAt(k - 1) + "") == -1) {
						tem1 = da.substring(0, k);
						if (tem1.equals("?")) {
							if (da.charAt(k) == '-' || da.charAt(k) == '+') {
								for (z = i; z < da.length(); ++z) {
									if ("0123456789E.".indexOf(da.charAt(z + 1)
											+ "") == -1) {
										tem1 = "";
										d1 = new BigDecimal(da.substring(i,
												z + 1));
										if (da.charAt(z + 1) == '?') {
											tem1 = "";
											break oout;
										}
										z++;
										break out;

									}

								}

								break;
							}
							tem1 = "";
						}

						d1 = new BigDecimal(da.substring(k, i));
						break;
					}
				}

				for (int k = z; k < da.length(); k++) {
					if ("0123456789E.".indexOf(da.charAt(k + 1) + "") == -1) {

						temp2 = da.substring(k + 1);
						if (temp2.equals("?")) {
							temp2 = "";
						}

						d2 = new BigDecimal(da.substring(z, k + 1));

						break;

					}
				}

				if (j == '+' || j == '-') {

					da = tem1 + (d1.add(d2).doubleValue()) + temp2;

				}

				return tempSum(this.trimes(da));
			}

		}

		return da.substring(1, da.length() - 1);
	}

	public String trimes(String s) {

		if (s.startsWith("?") && s.endsWith("?")) {
			return s.substring(1, s.length() - 1);
		}
		if (s.startsWith("?")) {
			return s.substring(1);

		}
		if (s.endsWith("?")) {
			return s.substring(0, s.length() - 1);
		}
		return s;
	}

	public String cutMerge(String da) {

		for (int m = 0; m < da.length(); m++) {
			int count = 0;
			if (da.charAt(m) == '+' || da.charAt(m) == '-') {

				for (int i = m; i < da.length(); i++) {

					if (da.charAt(i) == '-') {
						count++;
					}

					if (("0123456789E.?".indexOf(da.charAt(m - 1) + "") != -1)
							&& ("0123456789E.?".indexOf(da.charAt(m + 1) + "") != -1)) {
						break;
					}

					if ("0123456789E.".indexOf(da.charAt(i) + "") != -1) {

						if (count % 2 == 0) {
							da = da.substring(0, m) + "+" + da.substring(i);

							return this.cutMerge(da);
						} else {
							da = da.substring(0, m) + "-" + da.substring(i);

							return this.cutMerge(da);
						}

					}

				}
			}

		}
		return da;

	}

	public String cutdiv(String da) {

		for (int m = 0; m < da.length(); m++) {
			int count = 0;
			if (da.charAt(m) == '*' || da.charAt(m) == '/') {

				for (int i = m; i < da.length(); i++) {

					if (da.charAt(i) == '-') {
						count++;
					}

					if (("0123456789E.?".indexOf(da.charAt(m - 1) + "") != -1)
							&& ("0123456789E.?".indexOf(da.charAt(m + 2) + "") != -1)) {
						break;
					}

					if ("0123456789E.".indexOf(da.charAt(i) + "") != -1) {

						if (count % 2 == 0) {
							da = da.substring(0, m + 1) + "+" + da.substring(i);
							System.out.println(da);
							return this.cutdiv(da);
						} else {
							da = da.substring(0, m + 1) + "-" + da.substring(i);

							return this.cutdiv(da);
						}

					}

				}
			}

		}

		return da;

	}

	public static void main(String[] args) {
		new Count().setVisible(true);

	}

}
