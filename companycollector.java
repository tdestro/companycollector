package companycollector;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class companycollector {

	
	public static boolean end;
	public static WebDriver driver;
	public static List<String> allLinksOnThisPage;
	
	
	public static List<String> doConvertPageContentsToProfilesOnThisPageList() {

		int attempts = 0;
		List<String> returnlist = new LinkedList<String>();
		List<WebElement> elements;

		while (attempts < 100) {
			try {
				returnlist = new LinkedList<String>();
				// <div class="user-list-info">
				/*
				 * <a href="/JakeWharton">JakeWharton</a> Jake Wharton <ul
				 * class="user-list-meta text-small text-gray"> <li> <svg
				 * aria-hidden="true" class="octicon octicon-location"
				 * height="16" version="1.1" viewBox="0 0 12 16"
				 * width="12"><path fill-rule="evenodd"
				 * d="M6 0C2.69 0 0 2.5 0 5.5 0 10.02 6 16 6 16s6-5.98 6-10.5C12 2.5 9.31 0 6 0zm0 14.55C4.14 12.52 1 8.44 1 5.5 1 3.02 3.25 1 6 1c1.34 0 2.61.48 3.56 1.36.92.86 1.44 1.97 1.44 3.14 0 2.94-3.14 7.02-5 9.05zM8 5.5c0 1.11-.89 2-2 2-1.11 0-2-.89-2-2 0-1.11.89-2 2-2 1.11 0 2 .89 2 2z"
				 * ></path></svg> Pittsburgh, PA, USA </li>
				 * 
				 * <li> <svg aria-hidden="true" class="octicon octicon-mail"
				 * height="16" version="1.1" viewBox="0 0 14 16"
				 * width="14"><path fill-rule="evenodd"
				 * d="M0 4v8c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V4c0-.55-.45-1-1-1H1c-.55 0-1 .45-1 1zm13 0L7 9 1 4h12zM1 5.5l4 3-4 3v-6zM2 12l3.5-3L7 10.5 8.5 9l3.5 3H2zm11-.5l-4-3 4-3v6z"
				 * ></path></svg> <a class="email"
				 * href="mailto:jakewharton@gmail.com">jakewharton@gmail.com</a>
				 * </li>
				 * 
				 * <li> <svg aria-hidden="true" class="octicon octicon-clock"
				 * height="16" version="1.1" viewBox="0 0 14 16"
				 * width="14"><path fill-rule="evenodd"
				 * d="M8 8h3v2H7c-.55 0-1-.45-1-1V4h2v4zM7 2.3c3.14 0 5.7 2.56 5.7 5.7s-2.56 5.7-5.7 5.7A5.71 5.71 0 0 1 1.3 8c0-3.14 2.56-5.7 5.7-5.7zM7 1C3.14 1 0 4.14 0 8s3.14 7 7 7 7-3.14 7-7-3.14-7-7-7z"
				 * ></path></svg> <span><span class="join-label">Joined on
				 * </span><local-time class="join-date"
				 * datetime="2009-03-24T16:09:53Z" day="numeric" month="short"
				 * year="numeric" title="Mar 24, 2009, 12:09 PM EDT">Mar 24,
				 * 2009</local-time></span> </li> </ul> </div>
				 */
				elements = driver.findElements(By.xpath("//div[@class=\"user-list-info\"]/a"));
				for (WebElement we : elements) {
					String href = we.getAttribute("href");
					System.out.println(href);
					returnlist.add(href);
				}
				break;
			} catch (StaleElementReferenceException e) {
				System.out.println("stale retry");
			}
			attempts++;
		}
		
		if (returnlist.size() == 0)
			end = true;
		
		return returnlist;
		

	}

	public static boolean doGotoNextPage() throws UnsupportedEncodingException, InterruptedException {
		Random rand = new Random();
		int  n = rand.nextInt(5000) + 2000;
		Thread.sleep(n);
		List<WebElement> nexts;
		nexts = driver.findElements(By.xpath("//a[@class=\"next_page\"]"));
		if (nexts.size() < 1) {
			System.out.println("Can't find next button.");
			return false;
		}
		nexts.get(0).click();
		return true;
	}
	public static void gotoProfile(int i) throws InterruptedException
    {
		Random rand = new Random();
		int  n = rand.nextInt(5000) + 2000;
		Thread.sleep(n);
    driver.get(allLinksOnThisPage.get(i));
    }
	
	
	public static void main(String[] args) throws Exception {
		//
		// INSTALL PROFILE WITH ADBLOCK INTO FIREFOXDRIVER.
		//

		System.out.println("Starting webdriver.");

		System.setProperty("webdriver.chrome.driver", "/Users/anthonyodestro/eclipsework/lib/chromedriver");

		driver = new ChromeDriver();

		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		System.out.println("Deleting cookies.");
		driver.manage().deleteAllCookies();
		driver.get("http://www.github.com/login");

		String pass = JOptionPane.showInputDialog("Enter github password");

		// login
		// <input autocapitalize="off" autocorrect="off" autofocus="autofocus"
		// class="form-control input-block" id="login_field" name="login"
		// tabindex="1" type="text">

		// <input class="form-control form-control input-block" id="password"
		// name="password" tabindex="2" type="password">

		// <input class="btn btn-primary btn-block" data-disable-with="Signing
		// in…" name="commit" tabindex="3" type="submit" value="Sign in">

		driver.findElement(By.id("login_field")).clear();
		driver.findElement(By.id("login_field")).sendKeys("tdestro");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.xpath("//input[@value=\"Sign in\"]")).click();

		driver.get("https://github.com/search/advanced");
		// <input id="search_location" type="text" class="form-control
		// js-advanced-search-prefix" placeholder="San Francisco, CA"
		// tabindex="3" data-search-prefix="location:" data-search-type="Users">
		driver.findElement(By.id("search_location")).clear();
		driver.findElement(By.id("search_location")).sendKeys("Pittsburgh");
		driver.findElement(By.xpath("//button[.=\"Search\"]")).click();
		//
		
		
		allLinksOnThisPage = new LinkedList<String>();/*
		do {
			allLinksOnThisPage.addAll(doConvertPageContentsToProfilesOnThisPageList());

		} while (doGotoNextPage());		
		*/
		
		
		Scanner s = new Scanner(new java.io.FileInputStream(new java.io.File("/Users/anthonyodestro/eclipsework/companycollector/src/companycollector/pghlist")));
		while (s.hasNext()){
			allLinksOnThisPage.add(s.next());
		}
		s.close();
		
		
		
		//<div class="user-profile-bio"><div>I am the Director of Technical Advocacy at <a href="https://github.com/HashiCorp" class="user-mention">@HashiCorp</a>. I am an O'Reilly author, and I am passionate about reducing inequality in technology.</div></div>
		
		//<li aria-label="Organization" class="vcard-detail pt-1 css-truncate css-truncate-target" itemprop="worksFor" show_title="false"><svg aria-hidden="true" class="octicon octicon-organization" height="16" version="1.1" viewBox="0 0 16 16" width="16"><path fill-rule="evenodd" d="M16 12.999c0 .439-.45 1-1 1H7.995c-.539 0-.994-.447-.995-.999H1c-.54 0-1-.561-1-1 0-2.634 3-4 3-4s.229-.409 0-1c-.841-.621-1.058-.59-1-3 .058-2.419 1.367-3 2.5-3s2.442.58 2.5 3c.058 2.41-.159 2.379-1 3-.229.59 0 1 0 1s1.549.711 2.42 2.088C9.196 9.369 10 8.999 10 8.999s.229-.409 0-1c-.841-.62-1.058-.59-1-3 .058-2.419 1.367-3 2.5-3s2.437.581 2.495 3c.059 2.41-.158 2.38-1 3-.229.59 0 1 0 1s3.005 1.366 3.005 4"></path></svg><div>HashiCorp</div></li>
		
		for (int i = 0; i < allLinksOnThisPage.size(); i++) {
			gotoProfile(i);
			
			List<WebElement> elements = driver.findElements(By.xpath("//div[@class=\"user-profile-bio\"]/div"));
			for (WebElement we : elements) {
				String txt = we.getText();
				System.out.println(txt);
			}
			
			List<WebElement> elements0 = driver.findElements(By.xpath("//li[@aria-label=\"Organization\"]//div"));
			for (WebElement we : elements0) {
				String txt = we.getText();
				System.out.println(txt);
			}
		}
		

	}
}
