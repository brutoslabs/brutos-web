/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.util.captcha;

import javax.swing.ImageIcon;

/**
 * 
 * @author Brandao
 */
public class Imagem {

	public Imagem() {

	}

	public java.awt.image.BufferedImage getImage(String texto) {

		String[] fonts = new String[] { "Arial", "Tahoma", "Arial Black" };
		java.awt.Color cores[] = new java.awt.Color[] {
				new java.awt.Color(177, 242, 255),
				new java.awt.Color(115, 203, 193),
				new java.awt.Color(35, 250, 175) };

		java.awt.image.BufferedImage image = null;
		java.awt.Graphics2D g = null;
		try {

			image = new java.awt.image.BufferedImage(100, 50,
					java.awt.image.BufferedImage.TYPE_INT_RGB);
			g = (java.awt.Graphics2D) image.getGraphics();
			ImageIcon background = new ImageIcon(getClass().getResource(
					"/org/brandao/brutos/util/captcha/background.jpg"));
			g.drawImage(background.getImage(), 0, 0, null);
			// g.setColor( new java.awt.Color( 107, 172, 187 ) );
			// g.fillRect( 0, 0, 100, 30 );
			// g.setColor( new java.awt.Color( 0, 0, 0 ) );
			// g.drawRect( 0, 0, 99, 29 );
			// g.setColor( new java.awt.Color( 0, 0, 0 ) );
			// g.drawLine( 10, 10, 100, 10 );
			// g.drawLine( 100, 20, 10, 10 );
			// g.drawLine( 10, 20, 100, 15 );
			// g.drawLine( 10, 10, 100, 10 );
			int posx = 3;
			for (int i = 0; i < texto.length(); i++) {
				g.setColor(cores[i % cores.length]);
				g.setFont(new java.awt.Font(fonts[i % fonts.length], 0, 22));
				g.drawString(texto.charAt(i) + "", posx, 40 + ((i * 3) % 5));
				posx = posx + g.getFontMetrics().charWidth(texto.charAt(i));
			}
		} catch (Exception e) {
			return null;
		}
		return image;
	}
}
