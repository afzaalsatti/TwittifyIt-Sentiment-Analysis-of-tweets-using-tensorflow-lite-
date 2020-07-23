package com.afzaal.twittifyIt;

import java.util.ArrayList;
import java.util.List;

class Person {
    String name;
    String age;
    int photoId;

    Person(String name, String age, int photoId) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;
    }
    Person()
    {

    }


    private List<Person> persons;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    public List initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Donald Trump", "Donald John Trump is the 45th and current president of the United States. Before entering politics, he was a businessman and television personality. Trump was born and raised in the New York City borough of Queens, and received a bachelor's degree in economics from the Wharton School at the University of Pennsylvania. Wikipedia\n" +
                "Born: June 14, 1946 (age 73 years), Jamaica Hospital Medical Center, New York, United States\n" +
                "Height: 1.9 m\n" +
                "Party: Republican Party\n" +
                "Spouse: Melania Trump (m. 2005), Marla Maples (m. 1993–1999), Ivana Trump (m. 1977–1992)\n" +
                "Books: Trump: How to Get Rich, Trump: The Art of the Deal, MORE", R.drawable.trump));
        persons.add(new Person("Imran Khan", "Imran Ahmed Khan Niazi HI PP is the 22nd and current Prime Minister of Pakistan and the chairman of the Pakistan Tehreek-e-Insaf. Before entering politics, Khan was an international cricketer and captain of the Pakistan national cricket team, which he led to victory in the 1992 Cricket World Cup. Wikipedia\n" +
                "Born: October 5, 1952 (age 67 years), Lahore\n" +
                "Height: 1.88 m\n" +
                "Spouse: Bushra Bibi (m. 2018), Reham Khan (m. 2014–2015), Jemima Goldsmith (m. 1995–2004)\n" +
                "Education: Keble College (1972–1975), RGS Worcester, Aitchison College\n" +
                "Children: Sulaiman Isa Khan, Qasim Khan", R.drawable.imrankhan));
        persons.add(new Person("Narendra Modi", "Narendra Damodardas Modi is an Indian politician serving as the 14th and current Prime Minister of India since 2014. He was the Chief Minister of Gujarat from 2001 to 2014 and is the Member of Parliament for Varanasi. Wikipedia\n" +
                "Born: September 17, 1950 (age 69 years), Vadnagar, India\n" +
                "Height: 1.7 m\n" +
                "Spouse: Jashodaben Narendrabhai Modi (m. 1968)\n" +
                "Party: Bharatiya Janata Party\n" +
                "Education: Gujarat University (1983), University of Delhi (1978), School of Open Learning, University of Delhi", R.drawable.modi));


       persons.add(new Person("Vladimir Putin","Vladimir Vladimirovich Putin is the president of Russia since 2012, previously holding the position from 2000 until 2008. In between his presidential terms, he was also the prime minister of Russia under President Dmitry Medvedev. Wikipedia\n" +
               "Born: October 7, 1952 (age 67 years), Saint Petersburg, Russia\n" +
               "Height: 1.7 m\n" +
               "Spouse: Lyudmila Aleksandrovna Ocheretnaya (m. 1983–2014)\n" +
               "Previous offices: Prime Minister of Russia (2008–2012), MORE\n" +
               "Presidential terms: May 7, 2000 – May 7, 2008, May 7, 2012 –\n" +
               "Children: Yekaterina Putina, Mariya Putina",R.drawable.icon));
        persons.add(new Person("Xi Jinping","Xi Jinping is a Chinese politician serving as the general secretary of the Communist Party of China, president of the People's Republic of China, and chairman of the Central Military Commission. Wikipedia\n" +
                "Born: June 15, 1953 (age 66 years), Beijing, China\n" +
                "Height: 1.8 m\n" +
                "Education: Tsinghua University (1998–2002), MORE\n" +
                "Spouse: Peng Liyuan (m. 1987), Ke Lingling (m. 1979–1982)\n" +
                "Children: Xi Mingze",R.drawable.icon));







        persons.add(new Person("Inter-Services Public Relations","The Inter-Services Public Relations, is an organisation and a media wing of the Pakistan Armed Forces which broadcasts and coordinates military news and information to the country's civilian media and the civic society. Wikipedia\n" +
                "Director-General: Maj Gen Asif Ghafoor\n" +
                "Founded: 1949\n" +
                "Headquarters: Rawalpindi\n" +
                "Parent organization: Pakistan Armed Forces\n" +
                "Jurisdiction: Pakistan\n" +
                "Official language: Urdu; English",R.drawable.icon));
        persons.add(new Person("Pakistan Muslim League (N)","The Pakistan Muslim League is a centre-right conservative party in Pakistan. The party was recently in power until the appointment of an interim government led by Nasirul Mulk for the previous general election. Wikipedia\n" +
                "Leadership: Nawaz Sharif, Shehbaz Sharif (President)\n" +
                "Founder: Nawaz Sharif\n" +
                "Founded: 1993\n" +
                "Preceded by: Islami Jamhoori Ittehad",R.drawable.icon));
        persons.add(new Person("Bharatiya Janata Party","The Bharatiya Janata Party is one of the two major political parties in India, along with the Indian National Congress. As of 2019, it is the country's largest political party in terms of representation in the national parliament and state assemblies and is the world's largest party in terms of primary membership. Wikipedia\n" +
                "President: Amit Shah\n" +
                "Founders: Atal Bihari Vajpayee, Bhairon Singh Shekhawat, Lal Krishna Advani\n" +
                "Founded: April 6, 1980, India\n" +
                "Headquarters: 11 Ashoka Road, New Delhi - 110001\n" +
                "Alliance: National Democratic Alliance\n" +
                "Wings: BJP Mahila Morcha, Bharatiya Janata Yuva Morcha, BJP Minority Morcha, BJP Kisan Morcha\n" +
                "Newspaper: Kamal Sandesh\n" +
                "Preceded by: Janata Party, Bharatiya Jana Sangh",R.drawable.icon));
        persons.add(new Person("United States Army","he United States Army is the land warfare service branch of the U.S. Armed Forces. It is one of the eight U.S. uniformed services, and is designated as the Army of the United States in the U.S. Constitution. Wikipedia\n" +
                "Founded: June 14, 1775, United States\n" +
                "Headquarters location: Washington, D.C., United States\n" +
                "Commander-in-Chief: President Donald Trump\n" +
                "March: \"The Army Goes Rolling Along\" Play (help·info)\n" +
                "Role: Ground warfare\n" +
                "Chief of Staff: GEN James C. McConville",R.drawable.icon));
        persons.add(new Person("Indian Army","The Indian Army is the land-based branch and the largest component of the Indian Armed Forces. The President of India is the Supreme Commander of the Indian Army, and it is commanded by the Chief of Army Staff, who is a four-star general. Wikipedia\n" +
                "Headquarters: New Delhi, India\n" +
                "Motto: \"Service Before Self\"\n" +
                "Commander-in-Chief: President Ram Nath Kovind\n" +
                "Size: 1,237,117 active personnel; 960,000 reserve personnel; 209 manned aircraft\n" +
                "Founded: April 1, 1895, India\n" +
                "Chief of the Army Staff (COAS): General Bipin Rawat, PVSM, UYSM, AVSM, YSM, SM, VSM, ADC",R.drawable.icon));











        return  persons;
    }}