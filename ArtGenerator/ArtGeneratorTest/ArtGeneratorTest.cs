using ArtGenerator;

namespace ArtGeneratorTest
{
    public class ArtGeneratorTests
    {
        [SetUp]
        public void Setup()
        {

        }

        [Test]
        public void Test1()
        {
            Assert.Fail();
        }

        [Test]
        public void TestParser()
        {
            ArtGenerator.Parser parser = new ArtGenerator.Parser();
            String sampleText = "Test Text";
            parser.setParserString(sampleText);
            Assert.That(parser.getParserString(), Is.SameAs(sampleText));
        }
    }
}