import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class H05_20220808038 {
    public static void main(String[] args) throws PartyLimitReachedException, CharacterIsNotInPartyException {
        // Karakterlerin yaratılması
        Warrior warrior = new Warrior("Arthur");
        Cleric cleric = new Cleric("Merlin");
        Paladin paladin = new Paladin("Lancelot");

        // Parti yaratma
        Party party = new Party();

        try {
            // Karakterleri partiye ekle
            warrior.joinParty(party);
            cleric.joinParty(party);
            paladin.joinParty(party);

            // Karakter bilgilerini göster
            System.out.println(warrior);
            System.out.println(cleric);
            System.out.println(paladin);

            // Hasar ve iyileştirme
            warrior.takeDamage(10);
            cleric.takeDamage(5);
            paladin.takeHealing(5);

            // Yeniden karakter bilgilerini yazdır
            System.out.println("After damage and healing:");
            System.out.println(warrior);
            System.out.println(cleric);
            System.out.println(paladin);

            // Parti bilgisini yazdır
            System.out.println("Party Members:");
            System.out.println(party);

        } catch (AlreadyInPartyException e) {
            System.err.println(e.getMessage());
        }
    }    
    
}

interface Damageable {
    public void takeDamage(int damage);

    public void takeHealing(int healing);

    public boolean isAlive();
}

interface Caster {
    public void castSpell(Damageable target);

    public void learnSpell(Spell spell);
}

interface Combat extends Damageable {
    public void attack(Damageable target);

    public void lootWeapon(Weapon weapon);
}

interface Useable {
    public int use();
}

class PartyLimitReachedException extends Exception {
    public PartyLimitReachedException(String message) {
        super(message);
    }
}

class AlreadyInPartyException extends Exception {
    public AlreadyInPartyException(String message) {
        super(message);
    }
}

class CharacterIsNotInPartyException extends Exception {
    public CharacterIsNotInPartyException(String message) {
        super(message);
    }
}

class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String message) {
        super(message);
    }
}

class Spell implements Useable {

    @Override
    public int use() {
        return heal();
    }

    private int minHeal;
    private int maxHeal;
    private String name;

    public Spell(int minHeal, int maxHeal, String name) {
        this.minHeal = minHeal;
        this.maxHeal = maxHeal;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    private int heal() {
        Random random = new Random();
        return random.nextInt(minHeal, maxHeal + 1);
    }

}

class Weapon implements Useable {

    @Override
    public int use() {
        return attack();
    }

    private int minDamage;
    private int maxDamage;
    private String name;

    public Weapon(String name, int minDamage, int maxDamage) {
        this.name = name;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private int attack() {
        Random random = new Random();
        return random.nextInt(minDamage, maxDamage + 1);
    }
    
}

class Attributes {
    private int strength;
    private int intelligence;

    public Attributes() {
        setStrength(3);
        setIntelligence(3);
    }

    public Attributes(int strength, int intelligence) {
        this.strength = strength;
        this.intelligence = intelligence;
    }

    public void increaseStregth(int amount) {
        strength += amount;
    }

    public void increaseIntelligence(int amount) {
        intelligence += amount;
    }

    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public int getIntelligence() {
        return intelligence;
    }
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public String toString() {
        return "Attributes [Strength= " + strength + ", intelligence= " + intelligence + "]";
    }
    
}

abstract class Character implements Comparable<Character>, Damageable {

    @Override
    public int compareTo(Character other) {
        return Integer.compare(this.level, other.level);
    }

    @Override
    public boolean isAlive() {
        if(health > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void takeHealing(int healing) {
        health += healing;
    }
    
    private String name;
    protected int level;
    protected Attributes attributes;
    protected int health;

    public Character(String name, Attributes attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public abstract void levelUp();
    
    public String toString() {
        return getClass().getSimpleName() + "LvL: " + level + "-" + attributes;
    }
}

abstract class PlayableCharacter extends Character {
    private boolean inParty;
    private Party party;

    public PlayableCharacter(String name, Attributes attributes) {
        super(name, attributes);
    }

    public boolean isInParty() {
        return inParty;
    }

    public void levelUp() {
        level += 1;
    }

    public void joinParty(Party party) throws AlreadyInPartyException{
        if (inParty) {
            throw new AlreadyInPartyException("Character is already in a party.");
        }

        try {
            party.addCharacter(this);
            this.inParty = true;
            this.party = party;
        } 
        catch (PartyLimitReachedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void quitParty() throws CharacterIsNotInPartyException {
        try {
            if (inParty) {
                party.removeCharacter(this);
                inParty = false;
                party = null;
            } 
            else {
                throw new CharacterIsNotInPartyException("Character is not in a party.");
            }
        } 
        catch (CharacterIsNotInPartyException e) {
            System.out.println(e.getMessage());
        }

    }
}

abstract class NonPlayableCharacter extends Character {

    public NonPlayableCharacter(String name, Attributes attributes) {
        super(name, attributes);
    }
}

class Merchant extends NonPlayableCharacter {
    private Collection<Useable> inventory = new ArrayList<>();
	
	public Merchant(String name) {			
		super(name, new Attributes(0, 0));      
	}
	
	@Override
    public void levelUp(){
	}

    public void display() {
        for (Useable item : inventory) {
            System.out.println(item);
        }
    }

    public Useable buy(int itemNumber) throws ItemNotFoundException {
        try {
            return new ArrayList<>(inventory).get(itemNumber);
        }
        catch (IndexOutOfBoundsException e) {
            throw new ItemNotFoundException(getName());
        }
    }
    
    public void sell(Useable item) {
        inventory.add(item);
    }
}

class Skeleton extends NonPlayableCharacter implements Combat {
    private Collection<Useable> inventory = new ArrayList<>();

    public Skeleton(String name, Attributes attributes) {
        super(name, attributes);
    }

    @Override
    public void levelUp() {
        this.level++;
        this.attributes.setStrength(this.attributes.getStrength() + 1);
        this.attributes.setIntelligence(this.attributes.getIntelligence() + 1);
    }

    @Override
    public void takeHealing(int healing) {
        takeDamage(healing);
    }
 

    public void display() {
        for (Useable item : inventory) {
            System.out.println(item);
        }
    }

    public Useable buy(int itemNumber) throws ItemNotFoundException {
        try {
            return new ArrayList<>(inventory).get(itemNumber);
        }
        catch (IndexOutOfBoundsException e) {
            throw new ItemNotFoundException(getName());
        }
    }
    
    public void sell(Useable item) {
        inventory.add(item);
    }

    @Override
    public void attack(Damageable target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    public void lootWeapon(Weapon weapon) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lootWeapon'");
    }  
}

class Warrior extends PlayableCharacter implements Combat {
    private Collection<Useable> inventory = new ArrayList<>();
    private Useable weapon;

    public Warrior(String name) {
        super(name, new Attributes(4, 2));
        health = 35;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        this.attributes.increaseStregth(2);
        this.attributes.increaseIntelligence(1);
    }

    public void display() {
        for (Useable item : inventory) {
            System.out.println(item);
        }
    }

    public Useable buy(int itemNumber) throws ItemNotFoundException {
        try {
            return new ArrayList<>(inventory).get(itemNumber);
        }
        catch (IndexOutOfBoundsException e) {
            throw new ItemNotFoundException(getName());
        }
    }
    
    public void sell(Useable item) {
        inventory.add(item);
    }

    @Override
    public void attack(Damageable target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    public void lootWeapon(Weapon weapon) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lootWeapon'");
    } 
}

class Cleric extends PlayableCharacter implements Caster {
    private Collection<Useable> inventory = new ArrayList<>();
    private Useable spell;

    public Cleric(String name) {
        super(name, new Attributes(2, 4));
        health = 25;
    }

    public void levelUp() {
        super.levelUp();
        this.attributes.increaseStregth(1);
        this.attributes.increaseIntelligence(2);
    }

    public void display() {
        for (Useable item : inventory) {
            System.out.println(item);
        }
    }

    public Useable buy(int itemNumber) throws ItemNotFoundException {
        try {
            return new ArrayList<>(inventory).get(itemNumber);
        }
        catch (IndexOutOfBoundsException e) {
            throw new ItemNotFoundException(getName());
        }
    }
    
    public void sell(Useable item) {
        inventory.add(item);
    }

    @Override
    public void castSpell(Damageable target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'castSpell'");
    }

    @Override
    public void learnSpell(Spell spell) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'learnSpell'");
    }
}

class Paladin extends PlayableCharacter implements Combat, Caster {
    private Collection<Useable> inventory = new ArrayList<>();
    private Useable weapon;
    private Useable spell;

    public Paladin(String name) {
        super(name, new Attributes()); 
        health = 30;
    }

    public void display() {
        for (Useable item : inventory) {
            System.out.println(item);
        }
    }

    public Useable buy(int itemNumber) throws ItemNotFoundException {
        try {
            return new ArrayList<>(inventory).get(itemNumber);
        }
        catch (IndexOutOfBoundsException e) {
            throw new ItemNotFoundException(getName());
        }
    }
    
    public void sell(Useable item) {
        inventory.add(item);
    }

    @Override
    public void levelUp() {
        super.levelUp();
        if (level % 2 == 0) { 
            attributes.increaseStregth(2);
            attributes.increaseIntelligence(1);
        } else { // Odd level
            attributes.increaseStregth(1);
            attributes.increaseIntelligence(2);
        }
    }

    @Override
    public void castSpell(Damageable target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'castSpell'");
    }

    @Override
    public void learnSpell(Spell spell) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'learnSpell'");
    }

    @Override
    public void attack(Damageable target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    public void lootWeapon(Weapon weapon) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lootWeapon'");
    }
}

class Party {
    private static final int partyLimit = 8;
    private List<Combat> fighters = new ArrayList<>();
    private List<Caster> healers = new ArrayList<>();
    private int mixedCount;

    public void addCharacter(PlayableCharacter character) throws PartyLimitReachedException {
        if (fighters.size() + healers.size() >= partyLimit) {
            throw new PartyLimitReachedException("Party limit reached.");
        }
        if (character instanceof Combat) {
            fighters.add((Combat) character);
        }
        if (character instanceof Caster) {
            healers.add((Caster) character);
        }
        if (character instanceof Paladin) {
            mixedCount++;
        }
    }

    public void removeCharacter(PlayableCharacter character) throws CharacterIsNotInPartyException {
        if (fighters.remove(character)) {
            return;
        }
        if (healers.remove(character)) {
            if (character instanceof Paladin) {
                mixedCount--;
            }
            return;
        }
        throw new CharacterIsNotInPartyException("Character is not in the party.");
    }

    public String toString() {
        List<PlayableCharacter> allCharacters = new ArrayList<>();
        fighters.forEach(fighter -> allCharacters.add((PlayableCharacter) fighter));
        healers.forEach(healer -> {
            if (!allCharacters.contains(healer)) {
                allCharacters.add((PlayableCharacter) healer);
            }
        });

        Collections.sort(allCharacters, (c1, c2) -> Integer.compare(c1.getLevel(), c2.getLevel()));
        
        StringBuilder builder = new StringBuilder();
        for (PlayableCharacter character : allCharacters) {
            builder.append(character.getName()).append(" - Level: ").append(character.getLevel()).append("\n");
        }
        
        return builder.toString();
    }
}